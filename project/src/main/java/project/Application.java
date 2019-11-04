package project;

import project.view.BoardGUI;
import project.view.ImageResources;

import javax.swing.*;

public class Application {

	public static void main(String[] args) {

		ImageResources.getInstance();
		BoardGUI boardGUI = new BoardGUI(new DefaultBoard());
	}
};
