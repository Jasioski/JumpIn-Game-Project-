package project;

import project.view.BoardGui;
import project.view.ImageResources;

import javax.swing.*;

public class Application {

	public static void main(String[] args) {

		ImageResources.getInstance();
		BoardGui boardGUI = new BoardGui(new DefaultBoard());
	}
};
