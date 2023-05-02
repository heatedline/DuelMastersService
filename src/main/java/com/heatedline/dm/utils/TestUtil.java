package com.heatedline.dm.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TestUtil {
	
	public static void main(String[] args) {
		
		String s = "{{Hall of Fame}} {{Cardtable | image = SpiralGate(DM10).jpg | civilization = Water | type = Spell | ocgname = スパイラル・ゲート | cost = 2 | effect = {{Shield Trigger|spell}} ■ Choose a [[creature]] in the [[battle zone]] and return it to its [[owner]]'s [[hand]]. | ocgeffect = [[file:Shield Trigger.png|15px]] S・トリガー (この呪文をシールドゾーンから手札に加える時、コストを支払わずにすぐ唱えてもよい) ■ クリーチャーを1体選び、持ち主の手札に戻す。 | flavor = \"Come back when you can handle it.\" —{{Tooltip|Zaltan}} ([[DM-10]]) | flavor2 = そこだ! There! ([[DM-06]]) | flavor3 = こっちの水は辛いぜ。ママのミルクで我慢しな. The water here's harsh. Go back to your mama's milk. ([[DM-10]]) | flavor4 = 逆境も跳ね返せ! Coming back from an adversity! ([[DMD-02]]) | flavor5 = 渦巻く門が開く。時が巻き戻るが、戻って来ないものもある。The spiral gate opens. Even if time can be reversed, there are some things that cannot come back. ([[DMX-10]]) | flavor6 = 時の流れは大体巻き戻せない。だから、巻き戻せる時は遠慮なく。The flow of time cannot be reversed most of the time. That is why we make the most out of it when it does. ([[DMD-24]]) | mana = 1 | artist = Tomofumi Ogasawara | artist2 = Eiji Kaneda | artist3 = 青木たかお | set1 = DM-01 Base Set | setnum1 = 40/110 | R1 = Common | set2 = DM-01 Base Set (OCG) | setnum2 = 86/110 | R2 = Common | set3 = DM-06 Stomp-A-Trons of Invincible Wrath | setnum3 = 47/110 | R3 = Common | set4 = DM-06 Invincible Soul | setnum4 = 86/110 | R4 = Common | set5 = DM-10 Shockwaves of the Shattered Rainbow | setnum5 = 40/110 | R5 = Common | set6 = DM-10 Eternal Arms | setnum6 = 85/110 | R6 = Common | set7 = DMC-08 Endless Black Hole Deck | setnum7 = 54/82 | R7 = Common | set8 = DMC-16 Guardian Blue Deck | setnum8 = 54/78 | R8 = Common | set9 = DMC-17 Dark Warriors Deck | setnum9 = 54/78 | R9 = Common | set10 = DMD-02 Start Dash Deck: Water & Darkness | setnum10 = 11/15 | R10 = Common | set11 = DMX-10 Deck Builder Ogre Deluxe: Kirari! Leo Saga | setnum11 = 47/56 | R11 = Common | set12 = DMD-09 1st Deck Outrage Dash | setnum12 = 13/14 | R12 = Common | set13 = DMD-17 Beginning Dragon Deck: Crystal Memory Dragon | setnum13 = 14/14 | R13 = Common | set14 = DMX-20 Deck Ultimate Perfection!! Due-Max 160 ~Revolution & Invasion~ | setnum14 = 58/68 | R14 = Common | set15 = DMD-24 Masters Chronicle Deck: Bolmeteus Returns | setnum15 = 34/37 | R15 = Common | set16 = DMX-21 Masters Chronicle Pack: Comic of Heroes | setnum16 = 70/70 | R16 = No Rarity | set17 = DMEX-08 Mysterious Black Box Pack | setnum17 = 257/??? | R17 = Common | set18 = Promotional | setnum18 = P57/Y15 | R18 = Common }} {{Spiral}} [[Category:TCG]] [[Category:OCG]] [[Category:Bounce]]";
		
		String civilization = s.substring(s.indexOf(DMConstants.KEYWORD_CIVILIZATION + " = "), s.indexOf(DMConstants.KEYWORD_TYPE)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_CIVILIZATION + " = "), s.indexOf(DMConstants.KEYWORD_TYPE)).length() - 2).split(" = ")[1];
		String type = s.substring(s.indexOf(DMConstants.KEYWORD_TYPE + " = "), s.indexOf(DMConstants.KEYWORD_OCGNAME)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_TYPE + " = "), s.indexOf(DMConstants.KEYWORD_OCGNAME)).length() - 2).split(" = ")[1];
		String ocgname = s.substring(s.indexOf(DMConstants.KEYWORD_OCGNAME + " = "), s.indexOf(DMConstants.KEYWORD_COST)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_OCGNAME + " = "), s.indexOf(DMConstants.KEYWORD_COST)).length() - 2).split(" = ")[1];
		String cost = s.substring(s.indexOf(DMConstants.KEYWORD_COST + " = "), s.indexOf(DMConstants.KEYWORD_EFFECT)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_COST + " = "), s.indexOf(DMConstants.KEYWORD_EFFECT)).length() - 2).split(" = ")[1];
		String effect = s.substring(s.indexOf(DMConstants.KEYWORD_EFFECT + " = "), s.indexOf(DMConstants.KEYWORD_OCGEFFECT)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_EFFECT + " = "), s.indexOf(DMConstants.KEYWORD_OCGEFFECT)).length() - 2).split(" = ")[1];
		
		String flavor = "";
		List<String> flavorTextList = new ArrayList<String>();
		int flavorTextCount = StringUtils.countMatches(s, DMConstants.KEYWORD_FLAVOR);
		if(flavorTextCount > 1) {
			flavor = s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + " = "), s.indexOf(DMConstants.KEYWORD_FLAVOR + "2")).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + " = "), s.indexOf(DMConstants.KEYWORD_FLAVOR + "2")).length() - 2).split(" = ")[1];
			flavorTextList.add(flavor);
		}
		for(int i = 2; i < flavorTextCount; i++) {
			flavor = s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + i + " = "), s.indexOf(DMConstants.KEYWORD_FLAVOR + (i + 1))).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + i + " = "), s.indexOf(DMConstants.KEYWORD_FLAVOR + (i + 1))).length() - 2).split(" = ")[1];
			flavorTextList.add(flavor);
		}
		flavor = s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + flavorTextCount + " = "), s.indexOf(DMConstants.KEYWORD_MANA)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_FLAVOR + flavorTextCount + " = "), s.indexOf(DMConstants.KEYWORD_MANA)).length() - 2).split(" = ")[1];
		flavorTextList.add(flavor);
		
		String mana = s.substring(s.indexOf(DMConstants.KEYWORD_MANA + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST)).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_MANA + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST)).length() - 2).split(" = ")[1];
		
		String artist = "";
		List<String> artistTextList = new ArrayList<String>();
		int artistTextCount = StringUtils.countMatches(s, DMConstants.KEYWORD_ARTIST);
		if(artistTextCount > 1) {
			artist = s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST + "2")).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST + "2")).length() - 2).split(" = ")[1];
			artistTextList.add(artist);
		}
		for(int i = 2; i < artistTextCount; i++) {
			artist = s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + i + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST + (i + 1))).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + i + " = "), s.indexOf(DMConstants.KEYWORD_ARTIST + (i + 1))).length() - 2).split(" = ")[1];
			artistTextList.add(artist);
		}
		artist = s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + artistTextCount + " = "), s.indexOf(DMConstants.KEYWORD_SET + "1")).substring(0, s.substring(s.indexOf(DMConstants.KEYWORD_ARTIST + artistTextCount + " = "), s.indexOf(DMConstants.KEYWORD_SET + "1")).length() - 2).split(" = ")[1];
		artistTextList.add(artist);
		
		String set = "";
		int setTextCount = StringUtils.countMatches(s, DMConstants.KEYWORD_SET);
		System.out.println(setTextCount);
		
		System.out.println("civilization: " + civilization);
		System.out.println("type: " + type);
		System.out.println("ocgname: " + ocgname);
		System.out.println("cost: " + cost);
		System.out.println("effect: " + effect);
		System.out.println(DMConstants.KEYWORD_FLAVOR + ": " + flavorTextList.get(0));
		for(int i = 1; i < flavorTextList.size(); i++) {
			System.out.println(DMConstants.KEYWORD_FLAVOR + (i + 1) + ": " + flavorTextList.get(i));
		}
		System.out.println("mana: " + mana);
		System.out.println(DMConstants.KEYWORD_ARTIST + ": " + artistTextList.get(0));
		for(int i = 1; i < artistTextList.size(); i++) {
			System.out.println(DMConstants.KEYWORD_ARTIST + (i + 1) + ": " + artistTextList.get(i));
		}
		
		
	}

}
