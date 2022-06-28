package tommy.web.boardone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	// Singleton 적용
	private static BoardDAO instance = null;

	private BoardDAO() {
	}

	public static BoardDAO getInstance() {
		if (instance == null) {
			synchronized (BoardDAO.class) {
				instance = new BoardDAO();
			}
		}
		return instance;
	}

	// 게시판 작업의 기능 하나씩 메소드로 추가
	public void insertArticle(BoardVO article) { // 실제 DB 에 데이터를 넣어줄 메소드 생성
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 답글일 경우 1번 작업 원글의 ref, step, depth 복사 -> 이미 값 존재
		int num = article.getNum();
		int ref = article.getRef();
		int step = article.getStep();
		int depth = article.getDepth();

		int number = 0; // 새글일 경우, num 계산을 위한 임시 변수

		String sql = "";
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select max(num) from board");
			rs = pstmt.executeQuery();
			if (rs.next())
				number = rs.getInt(1) + 1; // 기존 글이 있으면 최대값 구해서 + 1 (번호 아껴쓰기)
			else
				number = 1; // 글이 하나도 없으면 number = 1 로 만들기

			if (num != 0) { // 답변글 -> step 수정
				// 2. 같은 그룹(ref) 내에서 나보다 step 이 큰 것에 대해서 + 1 (이미 답글로 1 씩 복사된 것 -> 따라서 0 > 1 이 되는
				// 셈)
				sql = "update board set step = step + 1 where ref = ? and step > ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeUpdate();
				// 3. 나의 step + 1, depth + 1
				step = step + 1;
				depth = depth + 1;
			} else { // 새글 -> ref 는 1, step = 0, depth = 0
				ref = number;
				step = 0;
				depth = 0;
			} // 게시글 추가 쿼리 작성
			sql = "insert into board(num, writer, email, subject, pass, regdate, ref, step, depth, content, ip) values(board_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getWriter());
			pstmt.setString(2, article.getEmail());
			pstmt.setString(3, article.getSubject());
			pstmt.setString(4, article.getPass());
			pstmt.setTimestamp(5, article.getRegdate());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, step);
			pstmt.setInt(8, depth);
			pstmt.setString(9, article.getContent());
			pstmt.setString(10, article.getIp());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	// 첫번째 전체 글의 개수를 가져올 메소드 생성
	public int getArticleCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select count(*) from board"); // 필드명을 넣을 때, 해당 필드명이 null 값인 경우 카운트에서 빠지므로 * 로
																		// 입력해주기
			rs = pstmt.executeQuery();

			if (rs.next())
				x = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return x;
	}

	// 게시판 전체 글을 가져오는 메소드
	public List<BoardVO> getArticles(int start, int end) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> articleList = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from (select rownum rnum, num, writer, email, subject, pass, regdate, readcount, ref, step, depth, content, ip from (select * from board order by ref desc, step asc)) where rnum >= ? and rnum <= ?");
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
//			pstmt = con.prepareStatement("select * from board order by num desc"); // 역순 -> 최신 글부터 먼저 출력, 수정 3
			rs = pstmt.executeQuery();

			if (rs.next()) {
				articleList = new ArrayList<BoardVO>(end - start + 1); // 수정 4
				do {
					BoardVO article = new BoardVO(); // article 초기화
					article.setNum(rs.getInt("num")); // DB 데이터의 num 값을 article 의 setNum 에 추가
					article.setWriter(rs.getString("writer"));
					article.setEmail(rs.getString("email"));
					article.setSubject(rs.getString("subject"));
					article.setPass(rs.getString("pass"));
					article.setRegdate(rs.getTimestamp("regdate"));
					article.setReadcount(rs.getInt("readcount"));
					article.setRef(rs.getInt("ref"));
					article.setStep(rs.getInt("step"));
					article.setDepth(rs.getInt("depth"));
					article.setContent(rs.getString("content"));
					article.setIp(rs.getString("ip"));
					articleList.add(article); // articleList 에 article 레코드 한 행씩 추가
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return articleList; // 게시판 리스트
	}

	// DB 에서 글 하나의 정보를 가져오는 메소드 생성
	public BoardVO getArticle(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO article = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("update board set readcount = readcount + 1 where num = ?"); // 조회수 +1
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement("select * from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new BoardVO();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return article;
	}

	// 글 수정 시에는 조회수 증가 X -> getArticle() 메소드와 같이 글 내용을 가져오면서 조회수 증가하는 부분의
	// upCount(int num) 분리
	public BoardVO updateGetArticle(int num) { // BoardVO 로 게시글의 내용 가져와야하며, num 으로 구분 지어주기 위해 num 은 인자값
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO article = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select * from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new BoardVO(); // article 초기화
				article.setNum(rs.getInt("num")); // DB 에서 값 가져와서 article 에 넣어주기
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return article;
	}

	// 글 수정 처리 메소드
	public int updateArticle(BoardVO article) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPasswd = "";
		String sql = "";
		int result = -1; // 결과값

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbPasswd = rs.getString("pass"); // 비밀번호 비교
				if (dbPasswd.equals(article.getPass())) { // article 에서 가져온 pass 와 DB 에서의 pass 가 동일하다면
					sql = "update board set writer = ?, email = ?, subject = ?";
					sql += ", content = ? where num = ?"; // 입력 받을 내용만 수정
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, article.getWriter()); // article 의 값 받아와서 1 번째 물음표에 넣어주기
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getContent());
					pstmt.setInt(5, article.getNum());
					pstmt.executeUpdate();
					result = 1; // 수정 성공
				} else {
					result = 0; // 수정 실패
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}

		return result;
	}
	
	// 글 삭제 처리하는 메소드 - 입력한 비밀번호와 DB 에서의 비밀번호를 비교해 글 삭제할 수 있도록 해줌
	public int deleteArticle(int num, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPasswd = ""; // DB 에서의 비밀번호
		int result = -1; // 글 삭제 실패
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPasswd = rs.getString("pass"); // DB 에서의 비밀번호를 dbPasswd 에 넣어주기
				if(dbPasswd.equals(pass)) { // dbPasswd 와 pass(내가 입력한 비밀번호) 가 동일할 때
					pstmt = con.prepareStatement("delete from board where num = ?");
					pstmt.setInt(1, num);
					pstmt.executeUpdate();
					result = 1; // 글 삭제 성공
				}
			}else	result = 0; // 비밀번호 틀림
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		return result;
	}
	
	
	
	
	
	
	

}
