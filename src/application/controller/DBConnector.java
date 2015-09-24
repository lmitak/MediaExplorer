package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.model.MediaStavka;

public class DBConnector {
	Connection con;
	Statement stmt;
	
	private static final String DB_TYPE = "org.sqlite.JDBC";
	private static final String DB_NAME = "jdbc:sqlite:media.db";
	
	//table names
	private static final String TABLE_MEDIA = "media";
	private static final String TABLE_GENRE = "genre";
	private static final String TABLE_ARTIST = "artist";
	private static final String TABLE_ALBUM = "album";
	private static final String TABLE_MEDIA_TYPE = "media_type";
	
	//column names
	//for media
	private static final String COLUMN_MEDIA_ID = "_media_id";
	private static final String COLUMN_MEDIA_NAME = "name";
	private static final String COLUMN_MEDIA_PATH = "path";
	private static final String COLUMN_MEDIA_ARTIST = "artist_id";
	private static final String COLUMN_MEDIA_VIEWERS = "viewers";
	private static final String COLUMN_MEDIA_RATE = "rate";
	private static final String COLUMN_MEDIA_DURATION = "duration";
	private static final String COLUMN_MEDIA_ALBUM = "album_id";
	private static final String COLUMN_MEDIA_GENRE = "genre_id";
	private static final String COLUMN_MEDIA_TYPE = "type_id";
	private static final String COLUMN_MEDIA_EXTENSION = "ext";
	
	//for genre
	private static final String COLUMN_GENRE_ID = "_genre_id";
	private static final String COLUMN_GENRE_NAME = "name";
	
	//for album
	private static final String COLUMN_ALBUM_NAME = "name";
	private static final String COLUMN_ALBUM_ID = "_album_id";
	private static final String COLUMN_ALBUM_GENRE = "genre_id";
	
	//for artist
	private static final String COLUMN_ARTIST_ID = "_artist_id";
	private static final String COLUMN_ARTIST_NAME = "name";
	private static final String COLUMN_ARTIST_INFORMATION = "information";
	
	//for media_type
	private static final String COLUMN_TYPE_ID = "_type_id";
	private static final String COLUMN_TYPE_NAME = "name";
	
	
	public DBConnector() {
		con = null;
		stmt = null;
	}
	
	public boolean openConnection(){
		try {
			Class.forName(DB_TYPE);
			con = DriverManager.getConnection(DB_NAME);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			if(con == null)
				return false;
		}
		return true;
	}
	
	private void executeUpdate(String sql) throws SQLException{
		stmt.executeUpdate(sql);
	}
	
	private ResultSet executeQuery(String sql) throws SQLException{
		return stmt.executeQuery(sql);
	}
	
	public boolean createDatabase(){
		
		String sqlType = "CREATE TABLE " + TABLE_MEDIA_TYPE + "("
				+ COLUMN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_TYPE_NAME + " TEXT"
				+ ");";
		
		String sqlGenre = "CREATE TABLE " + TABLE_GENRE + "("
				+ COLUMN_GENRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_GENRE_NAME + " TEXT"
				+ ");";
		String sqlAlbum = "CREATE TABLE " + TABLE_ARTIST + "("
				+ COLUMN_ARTIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_ARTIST_NAME + " TEXT,"
				+ COLUMN_ARTIST_INFORMATION + " TEXT"
				+ ");";
		String sqlArtist = "CREATE TABLE " + TABLE_ALBUM + "("
				+ COLUMN_ALBUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_ALBUM_NAME + " TEXT,"
				+ COLUMN_ALBUM_GENRE + " INTEGER,"
				+ "FOREIGN KEY (" + COLUMN_ALBUM_GENRE + ") REFERENCES " + TABLE_GENRE + "(" + COLUMN_GENRE_ID + ")"
				+ ");";
		String sqlMedia = "CREATE TABLE " + TABLE_MEDIA + "("
				+ COLUMN_MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_MEDIA_NAME + " TEXT,"
				+ COLUMN_MEDIA_PATH + " TEXT,"
				+ COLUMN_MEDIA_TYPE + " INTEGER,"
				+ COLUMN_MEDIA_ALBUM + " INTEGER,"
				+ COLUMN_MEDIA_ARTIST + " INTEGER,"
				+ COLUMN_MEDIA_GENRE + " INTEGERS,"
				+ COLUMN_MEDIA_RATE + " REAL DEFAULT 0,"
				+ COLUMN_MEDIA_DURATION + " REAL,"
				+ COLUMN_MEDIA_VIEWERS + " INTEGER DEFAULT 0,"
				+ COLUMN_MEDIA_EXTENSION + " TEXT,"
				+ "FOREIGN KEY (" + COLUMN_MEDIA_ALBUM + ") REFERENCES " + TABLE_ALBUM + "(" + COLUMN_ALBUM_ID + "),"
				+ "FOREIGN KEY (" + COLUMN_MEDIA_ARTIST + ") REFERENCES " + TABLE_ARTIST + "(" + COLUMN_ARTIST_ID + "),"
				+ "FOREIGN KEY (" + COLUMN_MEDIA_TYPE + ") REFERENCES " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_ID + "),"
				+ "FOREIGN KEY (" + COLUMN_MEDIA_GENRE + ") REFERENCES " + TABLE_GENRE + "(" + COLUMN_GENRE_ID + ")"
				+ ");";
		
		String sqlStartingGenre = "INSERT INTO " + TABLE_GENRE 
					+ "(" + COLUMN_GENRE_NAME + ")"
					+ " VALUES('Unknown')";
		
		try {
			executeUpdate(sqlType);
			executeUpdate(sqlGenre);
			executeUpdate(sqlAlbum);
			executeUpdate(sqlArtist);
			executeUpdate(sqlMedia);
			executeUpdate(sqlStartingGenre);
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('music')");
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('video')");
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('picture')");
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('music album')");
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('video album')");
			executeUpdate("INSERT INTO " + TABLE_MEDIA_TYPE + "(" + COLUMN_TYPE_NAME + ") VALUES('picture album')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertMedia(MediaStavka media){
		
		String sql = "INSERT INTO " + TABLE_MEDIA + "("
				+ COLUMN_MEDIA_NAME + "," 
				+ COLUMN_MEDIA_PATH + ","
				+ COLUMN_MEDIA_GENRE + ","
				+ COLUMN_MEDIA_DURATION + ","
				+ COLUMN_MEDIA_EXTENSION + ","
				+ COLUMN_MEDIA_TYPE + ")"
				+ " VALUES("
				+ "'" + media.getIme().getValue() + "' ,"
				+ "'" + media.getPath().getValue() + "' ,"
				+ "1,"
				+ media.getDuration() + ","
				+ "'" + media.getExtension() + "' ,"
				+ media.getTip().getId()
				+ ");";
		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public ArrayList<MediaStavka> giveAllMediaItems(){
		
		ArrayList<MediaStavka> list = new ArrayList<>();
		
		String sql = "SELECT * FROM " + TABLE_MEDIA;
		try {
			ResultSet rs = executeQuery(sql);
			do{
					list.add(new MediaStavka(
							rs.getInt(rs.findColumn(COLUMN_MEDIA_ID)),				//id
							rs.getString(rs.findColumn(COLUMN_MEDIA_NAME)), 		//name
							rs.getString(rs.findColumn(COLUMN_MEDIA_PATH)), 		//path(uri)
							rs.getInt(rs.findColumn(COLUMN_MEDIA_ARTIST)), 			//artist id
							rs.getInt(rs.findColumn(COLUMN_MEDIA_ALBUM)), 			//album id
							rs.getInt(rs.findColumn(COLUMN_MEDIA_TYPE)), 			//tip id
							rs.getInt(rs.findColumn(COLUMN_MEDIA_GENRE)), 			//genre id
							rs.getInt(rs.findColumn(COLUMN_MEDIA_VIEWERS)), 		//viewers
							rs.getDouble(rs.findColumn(COLUMN_MEDIA_DURATION)), 	//duration
							rs.getDouble(rs.findColumn(COLUMN_MEDIA_RATE)),			//rate
							rs.getString(rs.findColumn(COLUMN_MEDIA_EXTENSION))));	//file extension
			}while(rs.next());
		} catch (SQLException e) {
			list = null;
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean deleteMedia(int value) {
		
		String sql = "DELETE FROM " + TABLE_MEDIA
				+ " WHERE " + COLUMN_MEDIA_ID + " = " + value;
		try {
			executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	

}
