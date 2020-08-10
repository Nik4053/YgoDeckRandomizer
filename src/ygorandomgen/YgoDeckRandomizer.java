package ygorandomgen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YgoDeckRandomizer {

	private final List<String> mainDeckCards;
	private final List<String> extraDeckCards;
	
	
	public static void main(String[] args) throws IOException {
		new YgoDeckRandomizer();
	}
	public YgoDeckRandomizer() throws IOException {
		//Load card numbers
		Path mainDeckFile = Paths.get("MainDeckGOAT.txt");
		this.mainDeckCards = LoadCards(mainDeckFile);
		Path extraDeckFile = Paths.get("ExtraDeckGOAT.txt");
		this.extraDeckCards =  LoadCards(extraDeckFile);
		//Generate Deck 
		//SETTINGS: change size here
		Random rand = new Random(new Random().nextLong()); // the seed for generation
		int mainSize = 60;//rand.nextInt(20) +40;  //main deck size 40 + up to 20 = max 60 cards
		int extraSize = 15;//rand.nextInt(15);
		int sideSize = 15;//rand.nextInt(15);
		//END OF SETTINGS
		this.GenerateNewDeck(mainSize, extraSize, sideSize,rand);
		
	}
	
	private List<String> LoadCards(Path file) throws IOException {
		List<String> lines  = Files.readAllLines(file);
		return lines;
	}
	
	private void GenerateNewDeck(int mainSize, int extraSize, int sideSize, Random rand) throws IOException {		
		List<String> main = GenerateCards(mainSize, mainDeckCards,rand);
		List<String> extra = GenerateCards(extraSize, extraDeckCards,rand);
		//side deck
		List<String> allCards = new ArrayList<String>(mainDeckCards.size() + extraDeckCards.size());
		allCards.addAll(mainDeckCards);
		allCards.addAll(extraDeckCards);
		List<String> side = GenerateCards(sideSize, allCards,rand);
		//finished generating
		//write tot file	
		Path file = Paths.get("randomDeck.ydk");
		List<String> lines = new ArrayList<String>();
		lines.add("#created by randomiser");
		lines.add("#main");
		lines.addAll(main);
		lines.add("#extra");
		lines.addAll(extra);
		lines.add("!side");
		lines.addAll(side);
		Files.write(file, lines, StandardCharsets.UTF_8);
		
	}
	
	private List<String> GenerateCards(int size, List<String> allCards, Random rand) {
		List<String> list = new ArrayList<String>(size);
		for(int i = 0; i<size; i++) {
			list.add(allCards.get(rand.nextInt(allCards.size()))); // ignore possibility of 4 times the same card
		}
		return list;
	}
	
	
}
