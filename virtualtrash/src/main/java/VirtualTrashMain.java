import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class VirtualTrashMain extends Application {


    private RestTemplate restTemplate = new RestTemplate();

    public static final String HOST = "localhost";// "84.201.165.19";
    public static final String STATUS_URL = "http://" + HOST + ":8000/statusOperation?receiverId=2";
    public static final String CONFIRM_URL = "http://" + HOST + ":8000/confirmByReceiver?boxId=1&type=%22plastic%22&weight=0.04";

    private final ScheduledExecutorService closeService = Executors.newScheduledThreadPool(1);
    private ScheduledExecutorService requestService = Executors.newScheduledThreadPool(1);
    private ImageView firstImage;
    private ImageView secondImage;
    private StackPane containerPane;
    private ScheduledFuture<?> request;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Load Image");

        containerPane = new StackPane();
        firstImage = new ImageView(new Image("cap0.png"));
        secondImage = new ImageView(new Image("cap1.png"));
        setClose();
        //Adding HBox to the scene
        Scene scene = new Scene(containerPane);
        //primaryStage.setFullScreen(true);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.SPACE)) {
                confirm();
            }
        });
        scheduleRequest();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void scheduleRequest() {
        requestService = Executors.newScheduledThreadPool(1);
        request = requestService.scheduleAtFixedRate(() -> {
            String status = getStatus();
            System.out.println("CURRENT STATUS " + status);
            if (status.contains("IN_BOX")) {
                setOpen();
                requestService.shutdownNow();
            }
        }, 1, 500, TimeUnit.MILLISECONDS);
    }

    private void setOpen() {

        Platform.runLater(() -> {
            System.out.println("Open");
            containerPane.getChildren().setAll(secondImage);
        });

        System.out.println("Schedule delay");
        closeService.schedule(this::setClose, 5, TimeUnit.SECONDS);
    }

    private void setClose() {
        Platform.runLater(() -> {
            System.out.println("Close");
            containerPane.getChildren().setAll(firstImage);
        });
        scheduleRequest();


    }

    private String getStatus() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
            // Request to return JSON format
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("receiverId", "2");
            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(STATUS_URL, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String confirm() {
        try {
            System.out.println("CONFIRM");
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
            // Request to return JSON format
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("boxId", "1");
            headers.set("type", "glass");
            headers.set("weight", "0.2");

            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(CONFIRM_URL, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}