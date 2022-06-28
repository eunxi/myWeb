package tommy.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
        static { // 제일 먼저 드라이버 로딩부터 하라는 초기화 진행
                try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException cnfe) {
                        cnfe.printStackTrace();
                }
        }
        private ArrayList<Connection> free; // 사용하지 않은 커넥션 즉, 초기 커넥션을 저장하는 변수
        private ArrayList<Connection> used; // 사용중인 커넥션을 저장하는 변수
        private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        private String user = "mytest";
        private String password = "mytest";
        private int initialCons = 10; // 최초로 초기 커넥션수
        private int maxCons = 20; // 최대 커넥션수
        private int numCons = 0; // 총 Connection 수
        
        // 여기서부터 싱글톤 방식으로 진행(한 놈에 둘 이상이 못 붙게!)
        private static ConnectionPool cp;

        public static ConnectionPool getInstance() { // 커넥션풀 생성 - ConnectionPool.getInstance() 로 호출 가능
                try {
                        if (cp == null) { // cp 가 null 일 경우, ConnectionPool 생성
                                synchronized (ConnectionPool.class) {
                                        cp = new ConnectionPool();
                                }
                        }
                } catch (SQLException sqle) {
                        sqle.printStackTrace();
                }
                return cp;
        }

        private ConnectionPool() throws SQLException {
        		// 초기 커넥션 개수를 각각의 ArrayList에 저장할 수 있도록 초기 커넥션 수만큼 ArrayList 생성
                free = new ArrayList<Connection>(initialCons);
                used = new ArrayList<Connection>(initialCons);
                // initialCons 수만큼 Connection 생성(free)
                while (numCons < initialCons) {
                        addConnection();
                }
        }

        // free에 커넥션 객체를 저장
        private void addConnection() throws SQLException {
                free.add(getNewConnection());
        }

        // 새로운 커넥션 객체를 생성(추가)
        private Connection getNewConnection() throws SQLException {
                Connection con = null;
                try {
                        con = DriverManager.getConnection(url, user, password);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                System.out.println("About to connect to " + con); // ConnectionPool 정보
                ++numCons; // 컨넥션 생성될 때 마다 숫자 증가
                return con;
        }

        // free에 있는 커넥션을 used 로 옮기는 작업 => free--->used (Connection 할당)
        public synchronized Connection getConnection() throws SQLException {
                // free에 Connection이 없으면 maxCons만큼 Connection을 더 생성
                if (free.isEmpty()) {
                        while (numCons < maxCons) {
                                addConnection();
                        }
                }
                Connection _con;
                _con = free.get(free.size() - 1); // ArrayList 때문에 앞에서부터 사용하면 계속 앞당겨서 사용해야하기 때문에, 처음부터 뒤에서부터 사용
                free.remove(_con);
                used.add(_con);
                return _con;
        }

        // used에 있는 커넥션을 free로 반납
        public synchronized void releaseConnection(Connection _con) throws SQLException {
                boolean flag = false;
                if (used.contains(_con)) { // used 에 커넥션이 포함되어있다면
                        used.remove(_con); // 삭제
                        numCons--; // 현재 커넥션 감소
                        flag = true;
                } else {
                        throw new SQLException("ConnectionPool" + "에 있지않네요!!");
                }
                try {
                        if (flag) { // falg == true 
                                free.add(_con); // free 에 커넥션 추가
                                numCons++; // 현재 커넥션 +1
                        } else {
                                _con.close(); // 아닐경우, 커넥션 닫기
                        }
                } catch (SQLException e) {
                        try {
                                _con.close();
                        } catch (SQLException e2) {
                                e2.printStackTrace();
                        }
                }
        }

        public void closeAll() {// 모든 Connection 자원을 반납함 -> 커넥션을 뺏어서 강제로 종료해버리는 역할의 메소드
                // used에 있는 커넥션을 모두 삭제해 버림.
                for (int i = 0; i < used.size(); i++) {
                        Connection _con = (Connection) used.get(i); // used 에 있는 커넥션들을 가져와서 _con 에 넣어주기
                        used.remove(i--); // used 삭제
                        try {
                                _con.close();
                        } catch (SQLException sqle) {
                                sqle.printStackTrace();
                        }
                }
                // free에 있는 커넥션을 모두 삭제해 버림.
                for (int i = 0; i < free.size(); i++) {
                        Connection _con = (Connection) free.get(i); // free 에 있는 커넥션 가져와서 _con 에 넣어주기 
                        free.remove(i--); // free 삭제
                        try {
                                _con.close();
                        } catch (SQLException sqle) {
                                sqle.printStackTrace();
                        }
                }
        }

        public int getMaxCons() { // 최대
                return maxCons;
        }

        public int getNumCons() { // 현재
                return numCons;
        }
}