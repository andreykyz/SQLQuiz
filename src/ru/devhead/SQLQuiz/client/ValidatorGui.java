package ru.devhead.SQLQuiz.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*; 

public class ValidatorGui implements EntryPoint {
	
	//Компоненты дизайна
	HorizontalPanel upperPanel = new HorizontalPanel();
	VerticalPanel buttonPanel = new VerticalPanel();
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel tablePanel = new HorizontalPanel();
	
	Button executeButton = new Button("Execute query");
	Button getResultButton = new Button("Get result");
	
	TextArea queryPlace = new TextArea();
	
	Label queryResult = new Label();
	Label correctQueryResult = new Label();
	
	FlexTable tableQueryResult = new FlexTable(); 
	FlexTable tableSolution = new FlexTable();
	
	String[][] tableModelQueryResult; 
	
	@Override
	public void onModuleLoad() {
		
		getResultButton.addStyleName("getResultButton");
		
		buttonPanel.add(executeButton);
		buttonPanel.add(getResultButton);
		buttonPanel.setStyleName("buttonPanel");
		
		queryPlace.setCharacterWidth(100);
		queryPlace.setVisibleLines(5);
				
		upperPanel.add(queryPlace);
		upperPanel.add(buttonPanel);

		tableQueryResult.setText(0, 0, "data00");
		
		tablePanel.add(tableQueryResult);
		tablePanel.add(tableSolution);
		
		tableQueryResult.addStyleName("flexTable");
		
		tableSolution.setText(4, 4, "4");
		mainPanel.add(upperPanel);
		mainPanel.add(tablePanel);
		
		RootPanel.get("validator").add(mainPanel);
		
		queryPlace.setFocus(true);

		
		executeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				executeButtonClick();
			}

		});
	}
	
	void executeButtonClick() {
		tableQueryResult.clear(true);
		
	}
	
	void fireTableQueryResultUpdate() {
		
		tableQueryResult.clear(true);
		for (int i=0; (i<tableModelQueryResult.length) & (tableModelQueryResult != null); i++) {
			for (int j=0; (j<tableModelQueryResult[0].length) & (tableModelQueryResult[0] != null); j++) {
				tableQueryResult.setText(i, j, tableModelQueryResult[i][j]);
				if (i == 0) {
					//Стиль для шапки таблицы
					tableQueryResult.getRowFormatter().addStyleName(0, "flexTableHeader");
				}
				else {
					//Стиль для данных таблицы
					tableQueryResult.getCellFormatter().addStyleName(i, j, "flexTable");
				}
			}
		}
	}
	
	void fireTableSolutionUpdate() {
		//Заглушка
		tableQueryResult.clear(true);
	}
	
	/**
	   * Convert the string of JSON into JavaScript object.
	   */
	  private final native JsArray asArrayOfStockData(String json) /*-{
	    return eval(json);
	  }-*/;

}
