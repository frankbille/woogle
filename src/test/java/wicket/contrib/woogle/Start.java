/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.woogle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {
    private static final Log log = LogFactory.getLog(Start.class);

    /**
     * Main function, starts the Jetty 6 server.
     *
     * @param args
     */
    public static void main(String[] args) {
        Server jettyServer = null;
        try {
            jettyServer = new Server();

            SocketConnector conn = new SocketConnector();
            conn.setPort(8080);
            jettyServer.setConnectors(new Connector[]{conn});

            WebAppContext wah = new WebAppContext();
            wah.setContextPath("/woogle");
            wah.setWar("src/main/webapp");

            jettyServer.setHandler(wah);

            jettyServer.start();
        }
        catch (Exception e) {
            log.fatal("Could not start the Jetty server: " + e);
            if (jettyServer != null) {
                try {
                    jettyServer.stop();
                }
                catch (Exception e1) {
                    log.fatal("Unable to stop the jetty server: " + e1);
                }
            }
        }
    }
}
