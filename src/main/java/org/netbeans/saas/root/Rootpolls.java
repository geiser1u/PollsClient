/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.root;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * Rootpolls Service
 *
 * @author sylva
 */
public class Rootpolls {

    /**
     * Creates a new instance of Rootpolls
     */
    public Rootpolls() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param pollId
     * @return an instance of RestResponse
     */
    public static RestResponse pollById(String pollId) throws IOException {
        String[][] pathParams = new String[][]{{"{pollId}", pollId}};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection("https://pollsappproject.herokuapp.com/api/{pollId}", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
