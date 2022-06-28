package tommy.web.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {
        static { // ���� ���� ����̹� �ε����� �϶�� �ʱ�ȭ ����
                try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException cnfe) {
                        cnfe.printStackTrace();
                }
        }
        private ArrayList<Connection> free; // ������� ���� Ŀ�ؼ� ��, �ʱ� Ŀ�ؼ��� �����ϴ� ����
        private ArrayList<Connection> used; // ������� Ŀ�ؼ��� �����ϴ� ����
        private String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
        private String user = "mytest";
        private String password = "mytest";
        private int initialCons = 10; // ���ʷ� �ʱ� Ŀ�ؼǼ�
        private int maxCons = 20; // �ִ� Ŀ�ؼǼ�
        private int numCons = 0; // �� Connection ��
        
        // ���⼭���� �̱��� ������� ����(�� �� �� �̻��� �� �ٰ�!)
        private static ConnectionPool cp;

        public static ConnectionPool getInstance() { // Ŀ�ؼ�Ǯ ���� - ConnectionPool.getInstance() �� ȣ�� ����
                try {
                        if (cp == null) { // cp �� null �� ���, ConnectionPool ����
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
        		// �ʱ� Ŀ�ؼ� ������ ������ ArrayList�� ������ �� �ֵ��� �ʱ� Ŀ�ؼ� ����ŭ ArrayList ����
                free = new ArrayList<Connection>(initialCons);
                used = new ArrayList<Connection>(initialCons);
                // initialCons ����ŭ Connection ����(free)
                while (numCons < initialCons) {
                        addConnection();
                }
        }

        // free�� Ŀ�ؼ� ��ü�� ����
        private void addConnection() throws SQLException {
                free.add(getNewConnection());
        }

        // ���ο� Ŀ�ؼ� ��ü�� ����(�߰�)
        private Connection getNewConnection() throws SQLException {
                Connection con = null;
                try {
                        con = DriverManager.getConnection(url, user, password);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                System.out.println("About to connect to " + con); // ConnectionPool ����
                ++numCons; // ���ؼ� ������ �� ���� ���� ����
                return con;
        }

        // free�� �ִ� Ŀ�ؼ��� used �� �ű�� �۾� => free--->used (Connection �Ҵ�)
        public synchronized Connection getConnection() throws SQLException {
                // free�� Connection�� ������ maxCons��ŭ Connection�� �� ����
                if (free.isEmpty()) {
                        while (numCons < maxCons) {
                                addConnection();
                        }
                }
                Connection _con;
                _con = free.get(free.size() - 1); // ArrayList ������ �տ������� ����ϸ� ��� �մ�ܼ� ����ؾ��ϱ� ������, ó������ �ڿ������� ���
                free.remove(_con);
                used.add(_con);
                return _con;
        }

        // used�� �ִ� Ŀ�ؼ��� free�� �ݳ�
        public synchronized void releaseConnection(Connection _con) throws SQLException {
                boolean flag = false;
                if (used.contains(_con)) { // used �� Ŀ�ؼ��� ���ԵǾ��ִٸ�
                        used.remove(_con); // ����
                        numCons--; // ���� Ŀ�ؼ� ����
                        flag = true;
                } else {
                        throw new SQLException("ConnectionPool" + "�� �����ʳ׿�!!");
                }
                try {
                        if (flag) { // falg == true 
                                free.add(_con); // free �� Ŀ�ؼ� �߰�
                                numCons++; // ���� Ŀ�ؼ� +1
                        } else {
                                _con.close(); // �ƴҰ��, Ŀ�ؼ� �ݱ�
                        }
                } catch (SQLException e) {
                        try {
                                _con.close();
                        } catch (SQLException e2) {
                                e2.printStackTrace();
                        }
                }
        }

        public void closeAll() {// ��� Connection �ڿ��� �ݳ��� -> Ŀ�ؼ��� ��� ������ �����ع����� ������ �޼ҵ�
                // used�� �ִ� Ŀ�ؼ��� ��� ������ ����.
                for (int i = 0; i < used.size(); i++) {
                        Connection _con = (Connection) used.get(i); // used �� �ִ� Ŀ�ؼǵ��� �����ͼ� _con �� �־��ֱ�
                        used.remove(i--); // used ����
                        try {
                                _con.close();
                        } catch (SQLException sqle) {
                                sqle.printStackTrace();
                        }
                }
                // free�� �ִ� Ŀ�ؼ��� ��� ������ ����.
                for (int i = 0; i < free.size(); i++) {
                        Connection _con = (Connection) free.get(i); // free �� �ִ� Ŀ�ؼ� �����ͼ� _con �� �־��ֱ� 
                        free.remove(i--); // free ����
                        try {
                                _con.close();
                        } catch (SQLException sqle) {
                                sqle.printStackTrace();
                        }
                }
        }

        public int getMaxCons() { // �ִ�
                return maxCons;
        }

        public int getNumCons() { // ����
                return numCons;
        }
}