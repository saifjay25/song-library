//Saif Jame and Philip Aquilina
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class controller implements Initializable{
    @FXML
    public ListView<String> songList;

    @FXML
    private Button add;
    
    @FXML
    private Button delete;
    
    @FXML
    private Button edit;

    @FXML
    private TextField artist;

    @FXML
    private TextField song;

    @FXML
    private TextField album;
    
    @FXML
    private Text popup;

    @FXML
    private TextField year;
    
    @FXML
    private Text sart;

    @FXML
    private Text ssong;

    @FXML
    private Text salbum;

    @FXML
    private Text syear;
    
    public void initialize(URL location, ResourceBundle resources) {
        contentSelect();
    }

    //Will decide which type of content to display
    private void contentSelect(){
    	File file = new File("d.txt");
    	try {
    		Scanner ptr= new Scanner(file);
    		while(ptr.hasNextLine()) { //checks for next line
    			String sdetail=ptr.nextLine();
    			Scanner ls = new Scanner (sdetail);
    			ls.useDelimiter(" "); //space will be used to see the different c
    			while(ls.hasNext()) {
    				String info=ls.next();
    				if(info.indexOf("/")>=0) {
    					info=info.replace("/", " ");
    				}
    				String info2=ls.next();
    				if(info2.indexOf("/")>=0) {
    					info2=info2.replace("/", " ");
    				}

    				songList.getItems().add(info+", "+info2);
    				if(ls.hasNext()) {
    					String s=ls.next();
    					if(s.indexOf("/")>=0){
    						s=s.replace("/", " ");
    						array.getalbum().add(s);
    					}else{
    						array.getalbum().add(s);
    					}
    				}
    				if(ls.hasNext()) {
    					String s=ls.next();
    					if(s.indexOf("/")>=0){
    						s=s.replace("/", " ");
    						array.getyear().add(s);
    					}else{
    						array.getyear().add(s);
    					}
    				}
    				if(songList.getItems().size()==1) {
    					ssong.setText(info); 
    		        	sart.setText(info2);
    		        	salbum.setText(array.getalbum().get(0));
    		        	syear.setText(array.getalbum().get(0));
    				}
    			}
    			ls.close();
    		}
    		ptr.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    void addClicked() {

    	Song object= new Song(); //creates new song object
    	object.setName(song.getText()); //sets name
    	object.setArtist(artist.getText()); //sets artist
    	object.setAlbum(album.getText()); //sets album
    	object.setYear(year.getText()); //sets year
    	if(object.getName().isEmpty()|| object.getArtist().isEmpty()) { //checks if name of artist is empty
    		popup.setText("Please enter name of the song and artist"); //If name of artist is empty, this pops up
    		song.clear(); //clears song field
        	artist.clear(); //clears artist field
        	album.clear(); //clears album field
        	year.clear(); //clears year field
        	return;
    	}
    	String listName=object.getName()+", "+object.getArtist();
    	if(songList.getItems().size()==0) { //if there is nothing in the list
    		songList.getItems().add(listName); //inserts name of song and artist
    		array.ayear.add(object.getYear()); //saves the year
    		array.aalbum.add(object.getAlbum()); //saves the album
    	}else{ //puts list in order
    		songList=organize(songList, listName, object); 
    	}
    	if(ssong.getText().isEmpty()) { // puts the first song in the detail area by default
    		sart.setText(object.getArtist()); 
    		ssong.setText(object.getName());
    		syear.setText(object.getYear());
    		salbum.setText(object.getAlbum());
    	}
    	song.clear();
    	artist.clear();
    	album.clear();
    	year.clear();
    	save();
	}
    
    private ListView<String> organize(ListView<String> songList, String listName, Song object) {
		ListView<String> dummy= new ListView<String>();  //creates dummy list
		ArrayList<String> dyear= new ArrayList<String>(); //creates dummy array of years
		ArrayList<String> dalbum=new ArrayList<String>(); //creates dummy array of albums;
		int i=0; //loop variable
		int count=0; //counter variable
		String hold=songList.getItems().get(count); //used to obtain name of song
		int compare=listName.compareTo(hold); 
		if(compare==0) {
			popup.setText("Same song is in the list, please add another");
			return songList;
		}
		while(compare>0) { //checks to see if input is greater than items of list
			if(count+1<songList.getItems().size()) {
				count++;
			}else if(count+1==songList.getItems().size()){ //checks if it reaches the end
				songList.getItems().add(listName);
				array.ayear.add(object.getYear());
	    		array.aalbum.add(object.getAlbum());
				return songList;
			}
			hold=songList.getItems().get(count);
			compare=listName.compareTo(hold);
			if(compare==0) {
				popup.setText("Same song is in the list, please add another");
				return songList;
			}
			if(compare<0) { //stops when there is value in list greater than the input
				break;
			}
		}
		while(i!=songList.getItems().size()) {// adds to dummy list until i is to equal to count and new value is inputed, continues to put everything in list
			if(i==count) {
				dummy.getItems().add(listName);
				dyear.add(object.getYear());
				dalbum.add(object.getAlbum());
			}
			String temp=songList.getItems().get(i);
			dummy.getItems().add(temp);
			if (i<array.ayear.size()) {
				String temp1=array.ayear.get(i);
				dyear.add(temp1);
			}
			if(i<array.aalbum.size()) {
				String temp2=array.aalbum.get(i);
				dalbum.add(temp2);
			}
			i++;
		}
		
		songList.getItems().clear(); 
		int y=0;
		while(y!=dummy.getItems().size()) {//puts everything from dummy list to song list
			songList.getItems().add(dummy.getItems().get(y));
			y++;
		}
		array.ayear.clear();
		y=0;
		while(y!=dyear.size()) {//puts everything from dummy list to song list
			array.ayear.add(dyear.get(y));
			y++;
		}
		array.aalbum.clear();
		y=0;
		while(y!=dalbum.size()) {
			array.aalbum.add(dalbum.get(y));
			y++;
		}
		popup.setText("");
		return songList;
	}
    
	@FXML
	void listSelected() {
		String year="";
		String album="";
		if(songList.getItems().size() == 0) { //Checks if list is empty
   		 popup.setText("Nothing to be selected.");
   		 return;
		}
		int num=songList.getSelectionModel().getSelectedIndex();
		String info=songList.getSelectionModel().getSelectedItem();
		if(info==null) {
			popup.setText("click song");
	   		return;
		}
		String song=info.substring(0,info.indexOf(","));	
		String artist=info.substring(song.length()+2,info.length());
		if(num<array.ayear.size()) {
			year=array.ayear.get(num);
		}else {
			year="";
		}
		if(num<array.ayear.size()) {
			album=array.aalbum.get(num);
		}else {
			album="";
		}
		ssong.setText(song);
		sart.setText(artist);
		salbum.setText(album);
		syear.setText(year);
	}
	
     @FXML
     void editClicked() {
    	 String oalbum="";
    	 String oyear="";
    	 int index = -1;
    	 index = songList.getSelectionModel().getSelectedIndex(); //saves index of song to be edited
    	if(songList.getItems().size() == 0) { //Checks if list is empty
    		 popup.setText("Nothing to be edited");
    		 return;
    	 }else if( index == -1){
    		 popup.setText("Choose song to be edited.");
    		 return;
    	 }
    	
    	 String osong = songList.getItems().get(index); //Saves the string of the original song
     	 String oname = osong.substring(0,osong.indexOf(","));
     	 String oartist = osong.substring(osong.indexOf(" ")+1,osong.length());
     	 if(index<array.aalbum.size()) {
     		 oalbum = array.aalbum.get(index);
     	 }
     	 if(index<array.ayear.size()) {
     		 oyear = array.ayear.get(index);
     	 }
    	 int i = 0; //loop variable
    	 
    	 if(song.getText().isEmpty() && artist.getText().isEmpty() && album.getText().isEmpty() && year.getText().isEmpty()) {
    	 popup.setText("Enter new information.");
    	 return;
    	 }
    	 
    	 Song esong= new Song(); //creates new song object
         esong.setName(song.getText()); //sets song name
      	 esong.setArtist(artist.getText()); //sets artist
      	 esong.setAlbum(album.getText()); //sets album
         esong.setYear(year.getText()); //sets year
    	 
         String editsong = esong.getName() + ", " + esong.getArtist();
         String ename = editsong.substring(0,editsong.indexOf(","));
 	     String eartist = editsong.substring(editsong.indexOf(" ")+1,editsong.length());
 	     String ealbum = esong.getAlbum();
 	     String eyear =  esong.getYear();
 	     
 	     String c1 = oname + ", " + eartist;
 	     String c2 = ename + ", " + oartist;
    	 
    	 String tsong=""; //creates temporary song
    	 String ttalbum=""; //creates temporary album
    	 String ttyear=""; //creates temporary year
    	 
    	 ListView<String> tlist = new ListView<String>(); //creates temporary list
    	 ArrayList <String> talbum = new ArrayList<String>(); //creates temporary album
    	 ArrayList <String> tyear = new ArrayList<String>(); //creates temporary year
    	 
    	 
    	 while(i != songList.getItems().size()) {
    		int compare;
    		if(song.getText().isEmpty() && !(artist.getText().isEmpty())) { 
    			String cmp1 = songList.getItems().get(i);
    			compare = cmp1.compareTo(c1);
    			if(compare==0) {
    				popup.setText("Song is already present.");
    				return;
    			}
    			
    			i++;
    			
    		}else if(!(song.getText().isEmpty()) && artist.getText().isEmpty()) {
    			String cmp2 = songList.getItems().get(i);
    			compare = cmp2.compareTo(c2);
    			if(compare == 0) {
    				popup.setText("Song is already present.");
    				return;
    			}
    			
    			i++;
    			
    		}else if(!(song.getText().isEmpty()) && !(artist.getText().isEmpty())){
    			String cmp3 =  songList.getItems().get(i);
    			compare = cmp3.compareTo(editsong);
    			if(compare == 0) {
    				popup.setText("Song is already present.");
    				return;
    			}
    			
    			i++;
    		
    		}else {
    			break;
    		}
    	} 
     	
    	i = 0;
     while(i != songList.getItems().size()) { //adds everything to temporary lists
    	 
    	 if( i == index) { //adds edited 
    		 if(song.getText().isEmpty() && !(artist.getText().isEmpty())) {  //case: Song name is left blank
    			tlist.getItems().add(c1);
    			
    		 }else if(!(song.getText().isEmpty()) && artist.getText().isEmpty()) { //case: Artist name is left blank
    			 tlist.getItems().add(c2);		 
    	    		
    		 }else if(song.getText().isEmpty() && artist.getText().isEmpty()){
    			 tlist.getItems().add(osong);
    	    		
    		 }else {
    			 tlist.getItems().add(editsong);		 
    		 }
    		 
    		 if(album.getText().isEmpty() && !(year.getText().isEmpty())) { //if album is empty and year is not
	        	 talbum.add(oalbum);
	        	 tyear.add(eyear);
	        	    
    		 }else if(!(album.getText().isEmpty()) && year.getText().isEmpty()) {
    			 talbum.add(ealbum);
    			 tyear.add(oyear);
    			 
    		 }else if(album.getText().isEmpty() && year.getText().isEmpty()) {
    			 talbum.add(oalbum);
    			 tyear.add(oyear);
    			 
    		 }else{
    			 talbum.add(ealbum);
    			 tyear.add(eyear);
    		 }
	    		i++; 
    		
    	 }else{ //adds original
    	 tsong = songList.getItems().get(i);
    	 if (i<array.aalbum.size()) {
    		 ttalbum = array.aalbum.get(i);
    	 }
    	 if(i<array.ayear.size()) {
    		 ttyear =  array.ayear.get(i);
    	 }
    	 tlist.getItems().add(tsong);
    	 talbum.add(ttalbum);
    	 tyear.add(ttyear);
    	 
     	 i++;	
    	 }
    	 
     	}
    
     songList.getItems().clear();
     array.ayear.clear();
     array.aalbum.clear();
     
     i = 0;
     while(i != tlist.getItems().size()) {
    	 songList.getItems().add(tlist.getItems().get(i));
    	 i++;
     }
     
     i = 0;
     while(i !=talbum.size()) {
    	 array.aalbum.add(talbum.get(i));
    	 i++;
     }
     
     i = 0;
     while(i != tyear.size()) {
    	 array.ayear.add(tyear.get(i));
    	 i++;
     }
     
   song.clear();
   artist.clear();
   album.clear();
   year.clear(); 
   save();  	
     }

	 @FXML
	 void deleteClicked() {
		 if(songList.getItems().size() == 0) {
			 popup.setText("Nothing to be deleted");
			 sart.setText("");
	         ssong.setText("");
	    	 syear.setText("");
	    	 salbum.setText("");
	    	 popup.setText("");
	    	 save();
			 return;
		 }else {
		 popup.setText("Select song to be deleted.");
		 
		 }
		 
	     int index = songList.getSelectionModel().getSelectedIndex();
	     if(index==-1) {
	    	 popup.setText("click song");
		   	 return;
	     }
	     songList.getItems().remove(index);
	     if(index<array.ayear.size()) {
	    	 array.ayear.remove(index);
	     }
	     if(index<array.aalbum.size()) {
	    	 array.aalbum.remove(index);
	     }
	     	
	     if(songList.getItems().size() == 0) {
	    	 sart.setText("");
	         ssong.setText("");
	    	 syear.setText("");
	    	 salbum.setText("");
	    	 popup.setText("");
	    	 save();
	    	 return;
	     }
	    	
	     	String year="";
	     	String album="";
	    	if(index < songList.getItems().size()) { //select next item in list
	    		songList.scrollTo(index);
	    		songList.getFocusModel().focus(index);
	    		songList.getSelectionModel().select(index);
	    		String info=songList.getItems().get(index);
	    		String song=info.substring(0,info.indexOf(","));
	    		String artist=info.substring(info.indexOf(" ")+1,info.length());
	    		if (index<array.ayear.size()) {
	    			year=array.ayear.get(index);
	    		}
	    		if(index<array.aalbum.size()) {
	    			album=array.aalbum.get(index);
	    		}
	    		sart.setText(song); 
	    		ssong.setText(artist);
	    		syear.setText(year);
	    		salbum.setText(album);
	      
	    	}else if(index == songList.getItems().size()) {
	    		songList.scrollTo(index-1);
	    		songList.getFocusModel().focus(index-1);
	    		songList.getSelectionModel().select(index-1);
	    		String info=songList.getItems().get(index-1);
 				String song=info.substring(0,info.indexOf(","));
 				String artist=info.substring(info.indexOf(" ")+1,info.length());
 				if(index-1<array.ayear.size()) {
 					year=array.ayear.get(index-1);
 				}
 				if(index-1<array.aalbum.size()) {
 					album=array.aalbum.get(index-1);
 				}
 				sart.setText(song); 
 				ssong.setText(artist);
 				syear.setText(year);
 				salbum.setText(album);
	    	}
	    	save();
	 }
	 private void save() {
	 int i=0;
	 String year="";
	 String album="";
	    try {
	    	File file = new File("d.txt");
	    	FileWriter fileWriter = new FileWriter(file);
    		PrintWriter printWriter = new PrintWriter(fileWriter);
	    	while(i<songList.getItems().size()) {
	    		String s=songList.getItems().get(i);
	    		String s1=s.substring(0, s.indexOf(","));
	    		String s2=s.substring(s.indexOf(",")+2,s.length());
	    		if(s1.indexOf(" ")>=0) {
	    			s1=s1.replace(" ", "/");
	    			printWriter.print(s1);
	    		}else {
	    			printWriter.print(s1);
	    		}
	    		printWriter.print(" ");
	    		if(s2.indexOf(" ")>=0) {
	    			s2=s2.replace(" ", "/");
	    			printWriter.print(s2);
	    		}else {
	    			printWriter.print(s2);
	    		}
	    		printWriter.print(" ");
	    		if(i<array.getalbum().size() && !(array.getalbum().get(i).isEmpty())) {
	    			if(array.getalbum().get(i).indexOf(" ")>=0) {
	    				album=array.getalbum().get(i);
	    				album=album.replace(" ", "/");
	    				printWriter.print(album);
	    				printWriter.print(" ");
	    			}else {
	    				printWriter.print(array.getalbum().get(i));
	    				printWriter.print(" ");
	    			}
	    		}
	    		if(i<array.getyear().size() &&!(array.getyear().get(i).isEmpty())) {
	    			if(array.getyear().get(i).indexOf(" ")>=0) {
	    				year=array.getyear().get(i);
	    				year=year.replace(" ", "/");
	    				printWriter.print(year);
	    				printWriter.print(" ");
	    			}else {
	    				printWriter.print(array.getyear().get(i));
	    				printWriter.print(" ");
	    			}
	    		}
	    		printWriter.print("\n");
	    		fileWriter.flush();
	    		i++;
	    	}
	    	fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}

