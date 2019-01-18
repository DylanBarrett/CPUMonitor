/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcbk34cpumonitorfxml;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import java.text.DateFormat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 *
 * @author Dylan Barrett
 */
public class FXMLDocumentController implements Initializable {
  Dcbk34CPUModel cpuModel = new Dcbk34CPUModel();
  private boolean isRunning = false;
  public Timeline timeline;
  private DateFormat dateFormat;
  private Date date;
  private int recordNumber = 1;
  private int recordNumberPrint = 1;
  private double rotation = -135;
  
  @FXML
  public Label CPUDigitalDisplay;  
  @FXML
  public Label record1;
  @FXML
  public Label record2; 
  @FXML
  public Label record3;
  @FXML
  public VBox recordBox;
  @FXML 
  public ImageView dial; 
  @FXML 
  public Button start; 
  @FXML 
  public Button record;  
  @FXML
  
  private void start(ActionEvent event){
      if(!isRunning){
          isRunning = true;
          start.setText("Stop");
          start.setStyle("-fx-text-fill: #ff0000");
          record.setText("Record");
          timeline.play();
      } else {
          isRunning = false;
          start.setText("Start");
          start.setStyle("-fx-text-fill: #00ff11");
          record.setText("Reset");
          timeline.pause();
      }
  }
    public void reset(){
      isRunning = false;
      timeline.stop();
      record1.setText("--.--%");
      record2.setText("--.--%");
      record3.setText("--.--%");
      CPUDigitalDisplay.setText("--.--%");
      start.setText("Start");
      start.setStyle("-fx-text-fill: #07ed0a");
      record.setText("Record");
      dial.setRotate(-135);
      recordNumber = 1;
      recordNumberPrint = 1;
  }
  
  @FXML
  private void record(ActionEvent event){
      dateFormat = new SimpleDateFormat("hh:mm:ss");
      if(isRunning){
          switch(recordNumber){
              case 1: 
                  record1.setText(String.format("Record %d: %.2f%% at %s", recordNumberPrint, cpuModel.record(),dateFormat.format(date)));
                  recordNumber++;
                  recordNumberPrint++;
                  break;          
              case 2:
                  record2.setText(String.format("Record %d: %.2f%% at %s", recordNumberPrint, cpuModel.record(),dateFormat.format(date)));
                  recordNumber++;
                  recordNumberPrint++;
                  break;            
              case 3:
                  record3.setText(String.format("Record %d: %.2f%% at %s", recordNumberPrint, cpuModel.record(),dateFormat.format(date)));
                  recordNumber = 1;
                  recordNumberPrint++;
                  break;  
          }
      } else{
          reset();
      }
  }
  public void update(){
      CPUDigitalDisplay.setText(String.format("%.2f%%", 100*Dcbk34CPUModel.getCPUUsage()));
      rotation = cpuModel.getRotation();
      dial.setRotate(rotation);
  }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           timeline = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent) -> {
            update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }        
}