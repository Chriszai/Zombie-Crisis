package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


/**
 * 
 * @author 황성인
 *
 */
public class OptionPageController implements Initializable {
	private static final String click = "file:src/model/resources/click.wav";
	
	@FXML
	private AnchorPane volumepage;
	@FXML
	private Button closeBtn;
	@FXML
	private CheckBox mutebackvolume;
	@FXML
	private CheckBox muteeffectvolume;
	@FXML
	private Slider backvolume;
	@FXML
	private Slider effectvolume;
	@FXML
	private Label optionlabel;
	@FXML
	private Label backvolab;
	@FXML
	private Label effectvolab;
	@FXML
	private ImageView mute;
	@FXML
	private ImageView mute1;
		
	/**
	 * 
	 */
	@FXML
	private void closeClick() {

		volumepage.setTranslateY(-500);
		ViewManager.logo.setEffect(new DropShadow(65, Color.AQUA));
		SoundManager.playSound(click);
		
	}

	/**
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image image= new Image("view/resources/icon-muted-white.png");
		mute.setImage(image);
		mute1.setImage(image);

		mutebackvolume.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				if(mutebackvolume.isSelected()) {
					SoundManager.bamu = false;
					mutebackvolume.setText("OFF");
					backvolume.setDisable(true);
					SoundManager.clip.stop();
					
				}else {
					SoundManager.bamu = true;
					mutebackvolume.setText("ON");
					backvolume.setDisable(false);
					SoundManager.music(ViewManager.backsound);
				}			
			}		
		});
				
		mutebackvolume.setSelected(!SoundManager.bamu);
		
		muteeffectvolume.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(muteeffectvolume.isSelected()) {
					SoundManager.efmu = false;
					muteeffectvolume.setText("OFF");
					effectvolume.setDisable(true);
				}else {
					SoundManager.efmu = true;
					muteeffectvolume.setText("ON");
					effectvolume.setDisable(false);
				}
			
			}				
		});
		muteeffectvolume.setSelected(!(SoundManager.efmu));
		
		backvolume.valueProperty().addListener(new ChangeListener<Number>(){
		
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				SoundManager.vo = (backvolume.getValue()/100);
				SoundManager.clip.stop();
				SoundManager.music(ViewManager.backsound);
				backvolab.setText(String.valueOf((int)backvolume.getValue()));
			}
		});
				
		backvolume.setValue(SoundManager.vo*100);
		
		
		effectvolume.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				SoundManager.vol = (effectvolume.getValue()/100);
				effectvolab.setText(String.valueOf((int)effectvolume.getValue()));
			}
		});
		effectvolume.setValue(SoundManager.vol*100);
		
			
		optionlabel.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				optionlabel.setEffect(new DropShadow(15, Color.BLUE));
			}
			
		});
		optionlabel.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				optionlabel.setEffect(null);
			}
			
		});
		
	}

}
