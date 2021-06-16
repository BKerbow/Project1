package dev.kerbow.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.kerbow.models.Editor;
import dev.kerbow.models.GEJoin;
import dev.kerbow.models.Genre;
import dev.kerbow.repositories.GEJoinRepo;

public class Utils {
	private static Map<Editor, Set<Genre>> eToG = new HashMap<Editor, Set<Genre>>();
	private static Map<Genre, Set<Editor>> gToE = new HashMap<Genre, Set<Editor>>();
	private static GEJoinRepo gejr = new GEJoinRepo();
	
	public static Set<Genre> getGenres(Editor e){
		return eToG.get(e);
	}
	
	public static Set<Editor> getEditors(Genre g){
		return gToE.get(g);
	}
	
	public static void addEntry(Genre g, Editor e) {
		Set<Genre> gSet = eToG.get(e);
		Set<Editor> eSet = gToE.get(g);
		
		if(gSet == null) {
			gSet = new HashSet<Genre>();
		}
		
		if(eSet == null) {
			eSet = new HashSet<Editor>();
		}
		
		gSet.add(g);
		eSet.add(e);
		
		gToE.put(g, eSet);
		eToG.put(e, gSet);
		
		GEJoin j = new GEJoin(g, e);
		gejr.add(j);
	}
	
	public static void loadEntries() {
		Map<Integer, GEJoin> map = gejr.getAll();
		
		for (GEJoin j : map.values()) {
			Genre g = j.getGenre();
			Editor e = j.getEditor();
			
			Set<Genre> gSet = eToG.get(e);
			Set<Editor> eSet = gToE.get(g);
			
			if(gSet == null) gSet = new HashSet<Genre>();
			if(eSet == null) eSet = new HashSet<Editor>();
			
			gSet.add(g);
			eSet.add(e);
			
			eToG.put(e, gSet);
			gToE.put(g, eSet);
		}
	}

}
