package tommy.web.boardone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	// Singleton ����
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

	// �Խ��� �۾��� ��� �ϳ��� �޼ҵ�� �߰�
	public void insertArticle(BoardVO article) { // ���� DB �� �����͸� �־��� �޼ҵ� ����
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// ����� ��� 1�� �۾� ������ ref, step, depth ���� -> �̹� �� ����
		int num = article.getNum();
		int ref = article.getRef();
		int step = article.getStep();
		int depth = article.getDepth();

		int number = 0; // ������ ���, num ����� ���� �ӽ� ����

		String sql = "";
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select max(num) from board");
			rs = pstmt.executeQuery();
			if (rs.next())
				number = rs.getInt(1) + 1; // ���� ���� ������ �ִ밪 ���ؼ� + 1 (��ȣ �Ʋ�����)
			else
				number = 1; // ���� �ϳ��� ������ number = 1 �� �����

			if (num != 0) { // �亯�� -> step ����
				// 2. ���� �׷�(ref) ������ ������ step �� ū �Ϳ� ���ؼ� + 1 (�̹� ��۷� 1 �� ����� �� -> ���� 0 > 1 �� �Ǵ�
				// ��)
				sql = "update board set step = step + 1 where ref = ? and step > ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeUpdate();
				// 3. ���� step + 1, depth + 1
				step = step + 1;
				depth = depth + 1;
			} else { // ���� -> ref �� 1, step = 0, depth = 0
				ref = number;
				step = 0;
				depth = 0;
			} // �Խñ� �߰� ���� �ۼ�
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

	// ù��° ��ü ���� ������ ������ �޼ҵ� ����
	public int getArticleCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select count(*) from board"); // �ʵ���� ���� ��, �ش� �ʵ���� null ���� ��� ī��Ʈ���� �����Ƿ� * ��
																		// �Է����ֱ�
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

	// �Խ��� ��ü ���� �������� �޼ҵ�
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
//			pstmt = con.prepareStatement("select * from board order by num desc"); // ���� -> �ֽ� �ۺ��� ���� ���, ���� 3
			rs = pstmt.executeQuery();

			if (rs.next()) {
				articleList = new ArrayList<BoardVO>(end - start + 1); // ���� 4
				do {
					BoardVO article = new BoardVO(); // article �ʱ�ȭ
					article.setNum(rs.getInt("num")); // DB �������� num ���� article �� setNum �� �߰�
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
					articleList.add(article); // articleList �� article ���ڵ� �� �྿ �߰�
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

		return articleList; // �Խ��� ����Ʈ
	}

	// DB ���� �� �ϳ��� ������ �������� �޼ҵ� ����
	public BoardVO getArticle(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO article = null;

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("update board set readcount = readcount + 1 where num = ?"); // ��ȸ�� +1
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

	// �� ���� �ÿ��� ��ȸ�� ���� X -> getArticle() �޼ҵ�� ���� �� ������ �������鼭 ��ȸ�� �����ϴ� �κ���
	// upCount(int num) �и�
	public BoardVO updateGetArticle(int num) { // BoardVO �� �Խñ��� ���� �����;��ϸ�, num ���� ���� �����ֱ� ���� num �� ���ڰ�
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
				article = new BoardVO(); // article �ʱ�ȭ
				article.setNum(rs.getInt("num")); // DB ���� �� �����ͼ� article �� �־��ֱ�
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

	// �� ���� ó�� �޼ҵ�
	public int updateArticle(BoardVO article) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPasswd = "";
		String sql = "";
		int result = -1; // �����

		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbPasswd = rs.getString("pass"); // ��й�ȣ ��
				if (dbPasswd.equals(article.getPass())) { // article ���� ������ pass �� DB ������ pass �� �����ϴٸ�
					sql = "update board set writer = ?, email = ?, subject = ?";
					sql += ", content = ? where num = ?"; // �Է� ���� ���븸 ����
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, article.getWriter()); // article �� �� �޾ƿͼ� 1 ��° ����ǥ�� �־��ֱ�
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getContent());
					pstmt.setInt(5, article.getNum());
					pstmt.executeUpdate();
					result = 1; // ���� ����
				} else {
					result = 0; // ���� ����
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
	
	// �� ���� ó���ϴ� �޼ҵ� - �Է��� ��й�ȣ�� DB ������ ��й�ȣ�� ���� �� ������ �� �ֵ��� ����
	public int deleteArticle(int num, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbPasswd = ""; // DB ������ ��й�ȣ
		int result = -1; // �� ���� ����
		
		try {
			con = ConnUtil.getConnection();
			pstmt = con.prepareStatement("select pass from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPasswd = rs.getString("pass"); // DB ������ ��й�ȣ�� dbPasswd �� �־��ֱ�
				if(dbPasswd.equals(pass)) { // dbPasswd �� pass(���� �Է��� ��й�ȣ) �� ������ ��
					pstmt = con.prepareStatement("delete from board where num = ?");
					pstmt.setInt(1, num);
					pstmt.executeUpdate();
					result = 1; // �� ���� ����
				}
			}else	result = 0; // ��й�ȣ Ʋ��
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
