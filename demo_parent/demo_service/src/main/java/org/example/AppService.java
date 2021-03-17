package org.example;

/**
 * Hello world!
 *
 */
public class AppService {

    AppDao appDao = new AppDao();

    public  void testQueryService( String s ) {

        System.out.println( "进入service的testQueryService:"+s );
        appDao.query(s);
    }
}
