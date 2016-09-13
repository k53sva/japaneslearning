package com.melody.education.data;

import com.melody.education.R;
import com.melody.education.model.SyllabariesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 9/7/2016.
 */
public class SyllabariesManager {

    public static List<SyllabariesModel> getKatakana() {
        List<SyllabariesModel> list = new ArrayList<>();
        list.add(new SyllabariesModel("あ", "a", "", R.drawable.k_a));
        list.add(new SyllabariesModel("い", "i", "ii", R.drawable.k_i));
        list.add(new SyllabariesModel("う", "u", "", R.drawable.k_u));
        list.add(new SyllabariesModel("え", "e", "", R.drawable.k_e));
        list.add(new SyllabariesModel("お", "o", "", R.drawable.k_o));

        list.add(new SyllabariesModel("か", "ka", "", R.drawable.k_ka));
        list.add(new SyllabariesModel("き", "ki", "", R.drawable.k_ki));
        list.add(new SyllabariesModel("く", "ku", "", R.drawable.k_ku));
        list.add(new SyllabariesModel("け", "ke", "", R.drawable.k_ke));
        list.add(new SyllabariesModel("こ", "ko", "", R.drawable.k_ko));

        list.add(new SyllabariesModel("か", "ga", "", R.drawable.k_ga));
        list.add(new SyllabariesModel("き", "gi", "", R.drawable.k_gi));
        list.add(new SyllabariesModel("く", "gu", "", R.drawable.k_gu));
        list.add(new SyllabariesModel("け", "ge", "", R.drawable.k_ge));
        list.add(new SyllabariesModel("こ", "go", "", R.drawable.k_go));

        list.add(new SyllabariesModel("さ", "sa", "", R.drawable.k_sa));
        list.add(new SyllabariesModel("し", "shi", "", R.drawable.k_shi));
        list.add(new SyllabariesModel("す", "su", "", R.drawable.k_su));
        list.add(new SyllabariesModel("せ", "se", "", R.drawable.k_se));
        list.add(new SyllabariesModel("そ", "so", "", R.drawable.k_so));

        list.add(new SyllabariesModel("さ", "za", "", R.drawable.k_za));
        list.add(new SyllabariesModel("し", "ji", "", R.drawable.k_ji));
        list.add(new SyllabariesModel("す", "zu", "", R.drawable.k_zu));
        list.add(new SyllabariesModel("せ", "ze", "", R.drawable.k_ze));
        list.add(new SyllabariesModel("そ", "zo", "", R.drawable.k_zo));

        list.add(new SyllabariesModel("た", "ta", "", R.drawable.k_ta));
        list.add(new SyllabariesModel("ち", "chi", "", R.drawable.k_chi));
        list.add(new SyllabariesModel("つ", "tsu", "", R.drawable.k_tsu));
        list.add(new SyllabariesModel("て", "te", "", R.drawable.k_te));
        list.add(new SyllabariesModel("と", "to", "", R.drawable.k_to));

        list.add(new SyllabariesModel("た", "da", "", R.drawable.k_da));
        list.add(new SyllabariesModel("ち", "ji", "", R.drawable.k_ji));
        list.add(new SyllabariesModel("つ", "du", "", R.drawable.k_du));
        list.add(new SyllabariesModel("て", "de", "", R.drawable.k_de));
        list.add(new SyllabariesModel("と", "do", "", R.drawable.k_do));

        list.add(new SyllabariesModel("な", "na", "", R.drawable.k_na));
        list.add(new SyllabariesModel("に", "ni", "", R.drawable.k_ni));
        list.add(new SyllabariesModel("ぬ", "nu", "", R.drawable.k_nu));
        list.add(new SyllabariesModel("ね", "ne", "", R.drawable.k_ne));
        list.add(new SyllabariesModel("の", "no", "", R.drawable.k_no));

        list.add(new SyllabariesModel("は", "ha", "", R.drawable.k_ha));
        list.add(new SyllabariesModel("ひ", "hi", "", R.drawable.k_hi));
        list.add(new SyllabariesModel("ふ", "fu", "" , R.drawable.k_fu));
        list.add(new SyllabariesModel("へ", "he", "", R.drawable.k_he));
        list.add(new SyllabariesModel("ほ", "ho", "", R.drawable.k_ho));

        list.add(new SyllabariesModel("は", "ba", "", R.drawable.k_ba));
        list.add(new SyllabariesModel("ひ", "bi", "", R.drawable.k_bi));
        list.add(new SyllabariesModel("ふ", "bu", "" , R.drawable.k_bu));
        list.add(new SyllabariesModel("へ", "hbee", "", R.drawable.k_be));
        list.add(new SyllabariesModel("ほ", "bo", "", R.drawable.k_bo));

        list.add(new SyllabariesModel("は", "pa", "", R.drawable.k_pa));
        list.add(new SyllabariesModel("ひ", "pi", "", R.drawable.k_pi));
        list.add(new SyllabariesModel("ふ", "pu", "" , R.drawable.k_pu));
        list.add(new SyllabariesModel("へ", "pe", "", R.drawable.k_pe));
        list.add(new SyllabariesModel("ほ", "po", "", R.drawable.k_po));

        list.add(new SyllabariesModel("ま", "ma", "", R.drawable.k_ma));
        list.add(new SyllabariesModel("み", "mi", "", R.drawable.k_mi));
        list.add(new SyllabariesModel("む", "mu", "", R.drawable.k_mu));
        list.add(new SyllabariesModel("め", "me", "", R.drawable.k_me));
        list.add(new SyllabariesModel("も", "mo", "", R.drawable.k_mo));

        list.add(new SyllabariesModel("ら", "ra", "", R.drawable.k_ra));
        list.add(new SyllabariesModel("り", "ri", "", R.drawable.k_ri));
        list.add(new SyllabariesModel("る", "ru", "", R.drawable.k_ru));
        list.add(new SyllabariesModel("れ", "re", "", R.drawable.k_re));
        list.add(new SyllabariesModel("ろ", "ro", "", R.drawable.k_ro));

        list.add(new SyllabariesModel("や", "ya", "", R.drawable.k_ya));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("ゆ", "yu", "", R.drawable.k_yu));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("よ", "yo", "", R.drawable.k_yo));

        list.add(new SyllabariesModel("や", "wa", "", R.drawable.k_wa));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("ゆ", "wo", "", R.drawable.k_wo));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("よ", "n", "", R.drawable.k_n));
        return list;
    }

    public static List<SyllabariesModel> getHiragana() {
        List<SyllabariesModel> list = new ArrayList<>();
        list.add(new SyllabariesModel("あ", "a", "", R.drawable.h_a));
        list.add(new SyllabariesModel("い", "i", "ii", R.drawable.h_i));
        list.add(new SyllabariesModel("う", "u", "", R.drawable.h_u));
        list.add(new SyllabariesModel("え", "e", "", R.drawable.h_e));
        list.add(new SyllabariesModel("お", "o", "", R.drawable.h_o));

        list.add(new SyllabariesModel("か", "ka", "", R.drawable.h_ka));
        list.add(new SyllabariesModel("き", "ki", "", R.drawable.h_ki));
        list.add(new SyllabariesModel("く", "ku", "", R.drawable.h_ku));
        list.add(new SyllabariesModel("け", "ke", "", R.drawable.h_ke));
        list.add(new SyllabariesModel("こ", "ko", "", R.drawable.h_ko));

        list.add(new SyllabariesModel("か", "ga", "", R.drawable.h_ga));
        list.add(new SyllabariesModel("き", "gi", "", R.drawable.h_gi));
        list.add(new SyllabariesModel("く", "gu", "", R.drawable.h_gu));
        list.add(new SyllabariesModel("け", "ge", "", R.drawable.h_ge));
        list.add(new SyllabariesModel("こ", "go", "", R.drawable.h_go));

        list.add(new SyllabariesModel("さ", "sa", "", R.drawable.h_sa));
        list.add(new SyllabariesModel("し", "shi", "", R.drawable.h_shi));
        list.add(new SyllabariesModel("す", "su", "", R.drawable.h_su));
        list.add(new SyllabariesModel("せ", "se", "", R.drawable.h_se));
        list.add(new SyllabariesModel("そ", "so", "", R.drawable.h_so));

        list.add(new SyllabariesModel("さ", "za", "", R.drawable.h_za));
        list.add(new SyllabariesModel("し", "ji", "", R.drawable.h_ji));
        list.add(new SyllabariesModel("す", "zu", "", R.drawable.h_zu));
        list.add(new SyllabariesModel("せ", "ze", "", R.drawable.h_ze));
        list.add(new SyllabariesModel("そ", "zo", "", R.drawable.h_zo));

        list.add(new SyllabariesModel("た", "ta", "", R.drawable.h_ta));
        list.add(new SyllabariesModel("ち", "chi", "", R.drawable.h_chi));
        list.add(new SyllabariesModel("つ", "tsu", "", R.drawable.h_tsu));
        list.add(new SyllabariesModel("て", "te", "", R.drawable.h_te));
        list.add(new SyllabariesModel("と", "to", "", R.drawable.h_to));

        list.add(new SyllabariesModel("た", "da", "", R.drawable.h_da));
        list.add(new SyllabariesModel("ち", "ji", "", R.drawable.h_ji));
        list.add(new SyllabariesModel("つ", "du", "", R.drawable.h_du));
        list.add(new SyllabariesModel("て", "de", "", R.drawable.h_de));
        list.add(new SyllabariesModel("と", "do", "", R.drawable.h_do));

        list.add(new SyllabariesModel("な", "na", "", R.drawable.h_na));
        list.add(new SyllabariesModel("に", "ni", "", R.drawable.h_ni));
        list.add(new SyllabariesModel("ぬ", "nu", "", R.drawable.h_nu));
        list.add(new SyllabariesModel("ね", "ne", "", R.drawable.h_ne));
        list.add(new SyllabariesModel("の", "no", "", R.drawable.h_no));

        list.add(new SyllabariesModel("は", "ha", "", R.drawable.h_ha));
        list.add(new SyllabariesModel("ひ", "hi", "", R.drawable.h_hi));
        list.add(new SyllabariesModel("ふ", "fu", "" , R.drawable.h_fu));
        list.add(new SyllabariesModel("へ", "he", "", R.drawable.h_he));
        list.add(new SyllabariesModel("ほ", "ho", "", R.drawable.h_ho));

        list.add(new SyllabariesModel("は", "ba", "", R.drawable.h_ba));
        list.add(new SyllabariesModel("ひ", "bi", "", R.drawable.h_bi));
        list.add(new SyllabariesModel("ふ", "bu", "" , R.drawable.h_bu));
        list.add(new SyllabariesModel("へ", "be", "", R.drawable.h_be));
        list.add(new SyllabariesModel("ほ", "bo", "", R.drawable.h_bo));

        list.add(new SyllabariesModel("は", "pa", "", R.drawable.h_pa));
        list.add(new SyllabariesModel("ひ", "pi", "", R.drawable.h_pi));
        list.add(new SyllabariesModel("ふ", "pu", "" , R.drawable.h_pu));
        list.add(new SyllabariesModel("へ", "pe", "", R.drawable.h_pe));
        list.add(new SyllabariesModel("ほ", "po", "", R.drawable.h_po));

        list.add(new SyllabariesModel("ま", "ma", "", R.drawable.h_ma));
        list.add(new SyllabariesModel("み", "mi", "", R.drawable.h_mi));
        list.add(new SyllabariesModel("む", "mu", "", R.drawable.h_mu));
        list.add(new SyllabariesModel("め", "me", "", R.drawable.h_me));
        list.add(new SyllabariesModel("も", "mo", "", R.drawable.h_mo));

        list.add(new SyllabariesModel("ら", "ra", "", R.drawable.h_ra));
        list.add(new SyllabariesModel("り", "ri", "", R.drawable.h_ri));
        list.add(new SyllabariesModel("る", "ru", "", R.drawable.h_ru));
        list.add(new SyllabariesModel("れ", "re", "", R.drawable.h_re));
        list.add(new SyllabariesModel("ろ", "ro", "", R.drawable.h_ro));

        list.add(new SyllabariesModel("や", "ya", "", R.drawable.h_ya));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("ゆ", "yu", "", R.drawable.h_yu));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("よ", "yo", "", R.drawable.h_yo));

        list.add(new SyllabariesModel("や", "wa", "", R.drawable.h_wa));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("ゆ", "wo", "", R.drawable.h_wo));
        list.add(new SyllabariesModel("", "", ""));
        list.add(new SyllabariesModel("よ", "n", "", R.drawable.h_n));

        return list;
    }
}
