package mage.client.game;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import mage.choices.Choice;
import mage.client.components.MageUI;
import mage.interfaces.ServerState;
import mage.remote.Connection;
import mage.utils.MageVersion;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.mage.network.Client;
import org.mage.network.interfaces.MageClient;
import org.mage.network.messages.MessageType;

/**
 * Test for emulating the connection from multi mage clients.
 *
 * @author ayratn
 */
@Ignore
public class MultiConnectTest {

    private static final Logger logger = Logger.getLogger(MultiConnectTest.class);

    /**
     * Amount of games to be started from this test.
     */
    private static final Integer USER_CONNECT_COUNT = 200;

    private static final CountDownLatch latch = new CountDownLatch(USER_CONNECT_COUNT);

    private static final MageVersion version = new MageVersion(MageVersion.MAGE_VERSION_MAJOR, MageVersion.MAGE_VERSION_MINOR, MageVersion.MAGE_VERSION_PATCH,  MageVersion.MAGE_VERSION_MINOR_PATCH, MageVersion.MAGE_VERSION_INFO);

    private static volatile int connected;

    private final Object sync = new Object();
    private MageUI ui;

    private class ClientMock implements MageClient {

        private Client client;
        private final String username;
        private ServerState serverState;

        public ClientMock(String username) {
            this.username = username;
        }

        public void connect() {
            client = new Client(this);
            Connection connection = new Connection();
            connection.setUsername(username);
            connection.setHost("localhost");
            connection.setPort(17171);
            connection.setSSL(true);
            connection.setProxyType(Connection.ProxyType.NONE);

            client.connect(connection, version);
        }

        public MageVersion getVersion() {
            logger.info("getVersion");
            return version;
        }

        @Override
        public void connected(String message) {
            logger.info("connected: " + message);
            connected++;
        }

        @Override
        public void disconnected(boolean errorCall) {
            logger.info("disconnected");
        }

        public void showMessage(String message) {
            logger.info("showMessage: " + message);
        }

        public void showError(String message) {
            logger.info("showError: " + message);
        }

//        @Override
//        public void processCallback(ClientCallback callback) {
//            logger.info("processCallback");
//        }

        @Override
        public void inform(String title, String message, MessageType type) {
            if (type == MessageType.ERROR) {
                showError(message);
            }
            else {
                showMessage(message);
            }
        }

        @Override
        public void receiveChatMessage(UUID chatId, ChatMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void receiveBroadcastMessage(String message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void clientRegistered(ServerState state) {
            this.serverState = state;
        }

        @Override
        public ServerState getServerState() {
            return serverState;
        }

        @Override
        public void joinedTable(UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameStarted(UUID gameId, UUID playerId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void initGame(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameAsk(UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameTarget(UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameChooseAbility(UUID gameId, AbilityPickerView abilities) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameChoosePile(UUID gameId, String message, CardsView pile1, CardsView pile2) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameChooseChoice(UUID gameId, Choice choice) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gamePlayMana(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gamePlayXMana(UUID gameId, GameView gameView, String message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameSelectAmount(UUID gameId, String message, int min, int max) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameSelect(UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameEndInfo(UUID gameId, GameEndView view) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void userRequestDialog(UUID gameId, UserRequestMessage userRequestMessage) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameUpdate(UUID gameId, GameView gameView) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameInform(UUID gameId, GameClientMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameInformPersonal(UUID gameId, GameClientMessage message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameOver(UUID gameId, String message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void gameError(UUID gameId, String message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void sideboard(UUID tableId, DeckView deck, int time, boolean limited) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void construct(UUID tableId, DeckView deck, int time) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void startDraft(UUID draftId, UUID playerId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void draftInit(UUID draftId, DraftPickView draftPickView) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void draftUpdate(UUID draftId, DraftView draftView) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void draftPick(UUID draftId, DraftPickView draftPickView) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void draftOver(UUID draftId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void showTournament(UUID tournamentId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void tournamentStarted(UUID tournamentId) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void watchGame(UUID gameId, UUID chatId, GameView game) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public static void main(String[] argv) throws Exception {
        new MultiConnectTest().startMultiGames();
    }

    public void startMultiGames() throws Exception {
        for (int i = 0; i < USER_CONNECT_COUNT; i++) {
            logger.info("Starting game");
            connect(i);
        }
        latch.await();
        logger.info("Finished");
        logger.info("Connected: " + connected + " of " + USER_CONNECT_COUNT);
    }

    private void connect(final int index) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.fatal(null, e);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String username = "player" + index;
                ClientMock client = new ClientMock(username);
                client.connect();
                latch.countDown();
            }
        });
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.error("Error", e);
        }
    }
}
