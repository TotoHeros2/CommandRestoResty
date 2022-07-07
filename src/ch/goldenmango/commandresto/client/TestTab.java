package ch.goldenmango.commandresto.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import ch.goldenmango.commandresto.client.bean.Plat;
import ch.goldenmango.commandresto.client.service.RestoService;
import ch.goldenmango.commandresto.client.view.PlatCard;
import ch.goldenmango.commandresto.client.view.SpecialCard;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

public class TestTab extends Composite {

	private static TestTabUiBinder uiBinder = GWT.create(TestTabUiBinder.class);

	interface TestTabUiBinder extends UiBinder<Widget, TestTab> {
	}
	
	@UiField
	MaterialColumn listPlat;

	@UiField
	MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
	
	ArrayList<MaterialButton> buttons;
	
	MaterialRow rowSpecialite,rowPoulet,rowBoeuf,rowFM,rowPoisson; 
	
	MaterialRow entrees,porcs,canards;
	MaterialRow vegetariens,desserts,boissons;

	public TestTab() {
		initWidget(uiBinder.createAndBindUi(this));
		buttons = new ArrayList<MaterialButton>(Arrays.asList(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,button10));
		// curry from service
		ArrayList<Plat> specialites = RestoService.get().specialites();
//		specialites = RestoService.get().canards();// remove as uff8 not working
		// create row/col inside the tab curry
		int nbmax = specialites.size();
		rowSpecialite = new MaterialRow();
		listPlat.add(rowSpecialite);
		for (int i = 0; i < nbmax;i++)
		{
			MaterialColumn col = new MaterialColumn(12,6,4);
//			String noByType = String.valueOf(i + 1) + "/" + nbmax + ")";
			// vrai no
			String noByType = String.valueOf(specialites.get(i).getId()) +  ")";
			col.add(new SpecialCard(specialites.get(i),noByType));
			rowSpecialite.add(col);
		}
		// as test with 1 plat
//		rowBoeuf = new MaterialRow();
//		for (int i = 0; i < 1;i++)
//		{
//			MaterialColumn col = new MaterialColumn(12,6,4);
//			col.add(new PlatCard(specialites.get(0)));
//			rowBoeuf.add(col);
//		}
		addNbOfPlatToButtons();
		Window.alert("Le service de commande n'est pas encore disponible");

	}
	
	@UiHandler({"button1","button0","button2","button3","button4","button5","button6","button7","button8","button9","button10"})
	void onButtonClick(ClickEvent e) {
		MaterialButton selectedButton = (MaterialButton) e.getSource();
		for (MaterialButton button: buttons)
		{
			if (selectedButton == button)
			{
				button.setTextColor(Color.YELLOW);
			}
			else
			{
				button.setTextColor(Color.WHITE);
				
			}
		}
		
		switch (selectedButton.getTitle()) {
		
		case "Spécialité":
			listPlat.remove(0);
			listPlat.add(rowSpecialite);// already created
			break;
		case "Poulet":
			if (rowPoulet == null)
			{
				rowPoulet = getRow(RestoService.get().poulets());				
			}
			listPlat.remove(0);
			listPlat.add(rowPoulet);
			break;
		case "Boeuf":
			if (rowBoeuf == null)
			{
				rowBoeuf = getRow(RestoService.get().boeufs());							
			}
			listPlat.remove(0);
			listPlat.add(rowBoeuf);
			break;
		case "Crevette":
			if (rowFM == null)
			{
				rowFM = getRow(RestoService.get().crevettes());							
			
			}
			listPlat.remove(0);
			listPlat.add(rowFM);
			break;
			
		case "Poisson":
			if (rowPoisson == null)
			{
				rowPoisson = getRow(RestoService.get().poissons());										
			}
			listPlat.remove(0);
			listPlat.add(rowPoisson);
			break;			

		case "Entrée":
			if (entrees == null)
			{
				entrees = getRow(RestoService.get().entrees());				
			}
			listPlat.remove(0);
			listPlat.add(entrees);
			break;
		case "Porc":
			if (porcs == null)
			{
				porcs = getRow(RestoService.get().porcs());							
			}
			listPlat.remove(0);
			listPlat.add(porcs);
			break;
		case "Canard":
			if (canards == null)
			{
				canards = getRow(RestoService.get().canards());							
			
			}
			listPlat.remove(0);
			listPlat.add(canards);
			break;
			
		case "Végétarien":
			if (vegetariens == null)
			{
				vegetariens = getRow(RestoService.get().vegetariens());										
			}
			listPlat.remove(0);
			listPlat.add(vegetariens);
			break;			

		case "Dessert":
			if (desserts == null)
			{
				desserts = getRow(RestoService.get().desserts());										
			}
			listPlat.remove(0);
			listPlat.add(desserts);
			break;		
		case "Boisson":
			if (boissons == null)
			{
				boissons = getRow(RestoService.get().boissons());										
			}
			listPlat.remove(0);
			listPlat.add(boissons);
			break;	
			
			
		default:

			break;
		}
	}
	
	private MaterialRow getRow(ArrayList<Plat> listPlat)
	{
		int nbmax = listPlat.size();
		MaterialRow toReturn = new MaterialRow();
		int index = 1;
		for (Plat plat: listPlat)
		{
			MaterialColumn col = new MaterialColumn(12,6,4);
//			String noByType = String.valueOf(index) + "/" + nbmax + ")";
			// vrai no
			String noByType = String.valueOf(plat.getId()) +  ")";
			col.add(new PlatCard(plat,noByType));
			toReturn.add(col);
			index++;
		}
		return toReturn;
	}
	
	// add nb of plat on buttons
	private void addNbOfPlatToButtons()
	{
		for (MaterialButton button: buttons)
		{
			int noOfPlat = 0;
			switch (button.getText()) {
			
			case "Spécialité":
					noOfPlat = RestoService.get().specialites().size();
				break;
			case "Poulet":
				noOfPlat = RestoService.get().poulets().size();				

				break;
			case "Boeuf":
				noOfPlat = RestoService.get().boeufs().size();				
				break;
			case "Crevette":
				noOfPlat = RestoService.get().crevettes().size();				
				break;
				
			case "Poisson":
				noOfPlat = RestoService.get().poissons().size();				
				break;			

			case "Entrée":
				noOfPlat = RestoService.get().entrees().size();				
				break;
			case "Porc":
				noOfPlat = RestoService.get().porcs().size();				
				break;
			case "Canard":
				noOfPlat = RestoService.get().canards().size();				
				break;
				
			case "Végétarien":
				noOfPlat = RestoService.get().vegetariens().size();				
				break;			

			case "Dessert":
				noOfPlat = RestoService.get().desserts().size();				
				break;		
			case "Boisson":
				noOfPlat = RestoService.get().boissons().size();				
				break;	
				
				
			default:

				break;
			}
			button.setTitle(button.getText());
			button.setText(button.getText() + " (" + noOfPlat + ")");
//			GWT.log(button.getTitle());
		}
	}
}
