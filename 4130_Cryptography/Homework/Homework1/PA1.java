import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PA1{
    public static void main(String[] args){

        Hashtable<String, String> sub = new Hashtable<String, String>();
        sub.put(".", " ");
        sub.put("s", "a");
        sub.put("k", "e");
        sub.put("-", "t");
        sub.put("z", "r");
        sub.put("q", "n");
        sub.put("\"", "i");
        sub.put("x", "s");
        sub.put("\'", "o");
        sub.put("r", "h");
        sub.put("g", "l");
        sub.put("a", "d");
        sub.put("l", "c");
        sub.put(" ", "u");
        sub.put("m", "k");
        sub.put("v", "b");
        sub.put(",", "m");
        sub.put(";", "\'");
        sub.put("h", "w");
        sub.put("t", "f");
        sub.put("e", "g");
        sub.put("j", ",");
        sub.put("d", "p");
        sub.put("f", "y");
        sub.put("b", ".");
        sub.put("w", "v");
        sub.put("u", "j");
        sub.put("o", "z");
        sub.put("y", "\"");
        sub.put("n", "-");
        sub.put("i", "x");
        sub.put("c", "q");
        sub.put("p", ";");


        String text = "-r\' xsqax.\'t.fkszx.se\'j.t\"wk.stz\"lsq.-z\"vkx.hsz.\'wkz.s.,k-k\'z\"-k.l\'q-s\"q\"qe.-rk.,k-sg.w\"vzsq\" ,b.\'qk.hszz\"\'z.\"qekx-x.s.yrksz-nxrsdka.rkzvy.sttkl-ka.vf.-rk.,k-sg.sqa.es\"qx.x dkzr ,sq.sv\"g\"-\"kxj.vkl\',\"qe.-rk.t\"zx-.yvgslm.dsq-rkzyb.rk. q\"-kx.sgg.v -.-rk.usvsz\".-z\"vk.-\'.t\'z,.-rk.qs-\"\'q.\'t.hsmsqasb.\'wkz.lkq- z\"kxj.-rk.hsmsqasqx. xk.-rk.w\"vzsq\" ,.-\'.akwkg\'d.sawsqlka.-klrq\'g\'ef.sqa.\"x\'gs-k.-rk,xkgwkx.tz\',.-rk.h\'zga.vf.d\'x\"qe.sx.s.-r\"za.h\'zga.l\' q-zfb.\"q.-rk.kszgf.q\"qk-\"kxj.hsmsqas;x.m\"qe.-;lrsms.w\"x\"-x.r\"x.vz\'-rkz.q;u\'v j.hr\'.\"x.h\'zm\"qe. qakzl\'wkz.\"q.\'smgsqaj.lsg\"t\'zq\"sb.-;lrsms.sll xkx.q;u\'v .\'t.sxx\"x-\"qe.vgslmn,szmk-.sz,x.aksgkz. gfxxkx.mgs k.h\"-r.x-ksg\"qe.w\"vzsq\" ,.tz\',.hsmsqasb.q;u\'v ;x.dsz-qkz.zkwksgx.rk.\"x.o z\"j.sq\'-rkz. qakzl\'wkz.hsmsqasqj.sqa.l\'qt\"z,x.-;lrsms;x.x xd\"l\"\'qxb.\"q.-rk.dzkxkq-.asfj.t\'gg\'h\"qe.-;lrsms;x.aks-rj.r\"x.x\'q.-;lrsggs.zk- zqx.-\'.hsmsqas.-\'.sxx ,k.-rk.-rz\'qkb.rk.sqa.\'m\'fkj.-rk.gksakz.\'t.-rk.a\'zs.,\"gsuk.zke\",kq-j.ki-zsl-.-;lrsggs;x.king\'wkz.qsm\"s.tz\',.sq. qakzl\'wkz.sxx\"eq,kq-.x\'.xrk.lsq.s--kqa.r\"x.l\'z\'qs-\"\'q.lkzk,\'qf.h\"-r.r\"x.,\'-rkz.zs,\'qas.sqa.f\' qekz.x\"x-kz.xr z\"b.s-.-rk.lkzk,\'qfj.-rk.usvsz\".-z\"vk;x.gksakz.,;vsm .lrsggkqekx.-;lrsggs.t\'z.-rk.lz\'hq.\"q.z\"- sg.l\',vs-b.-;lrsggs.aktks-x.,;vsm .sqa.dkzx sakx.r\",.-\'.f\"kga.zs-rkz.-rsq.a\"kb.hrkq.mgs k.sqa.r\"x.sll\',dg\"lk.kz\"m.x-kwkqx.x-ksg.s.hsmsqasq.sz-\"tsl-.tz\',.s.g\'qa\'q., xk ,j.-;lrsggs;x.tz\"kqa.sqa.\'m\'fk;x.g\'wkz.h;msv\". zekx.r\",.-\'.vz\"qe.mgs k.vslm.sg\"wkb.-;lrsggsj.\'m\'fkj.sqa.qsm\"s.-zswkg.-\'.v xsqj.x\' -r.m\'zksj.hrkzk.mgs k.dgsqx.-\'.xkgg.-rk.sz-\"tsl-.-\'.l\"s.sekq-.kwkzk--.mb.z\'xxb.s.t\"zkt\"er-.kz d-xj.sqa.mgs k.s--k,d-x.-\'.tgkk.v -.\"x.ls er-.vf.-;lrsggsj.hr\'.zkg l-sq-gf.zkgksxkx.r\",.-\'.z\'xx;.l x-\'afb.mgs k.-kggx.z\'xx.-rs-.hsmsqas;x.\"q-kzqs-\"\'qsg.\",sek.\"x.s.tz\'q-.t\'z.s.-klrq\'g\'e\"lsggf.sawsqlka.l\"w\"g\"os-\"\'qb.kz\"m.s--slmx.sqa.ki-zsl-x.mgs k.sx.z\'xx.\"x.ezswkgf.\"qu zka.dz\'-kl-\"qe.qsm\"sb.zs-rkz.-rsq.d zx k.mgs kj.-;lrsggs.-smkx.z\'xx.-\'.hsmsqasj.hrkzk.-rk\"z.-klrq\'g\'ef.lsq.xswk.r\",b.hr\"gk.xr z\".rksgx.z\'xxj.-;lrsggs.l\'qtz\'q-x.o z\".sv\' -.q;u\'v b.o z\".kidgs\"qx.-rs-.q;u\'v .dgsqqka.-\'.xrszk.hsmsqas;x.-klrq\'g\'ef.h\"-r.dk\'dgk.\'t.stz\"lsq.akxlkq-.sz\' qa.-rk.h\'zga.-\'.rkgd.-rk,.l\'qc kz.-rk\"z.\'ddzkxx\'zxb.sx.-;lrsms.szzkx-ka.q;u\'v j.-rk.gs--kz.s--slmka.o z\".sqa.t\'zlka.-;lrsms.-\'.m\"gg.r\",b.-;lrsms.\'zakzka.o z\".-\'.g\"k.-rs-.q;u\'v .rsa.a\"xsddkszka.sqa.gkt-.vkr\"qa.q;u\'v ;x.s,kz\"lsq.x\'q.-\'.,s\"q-s\"q.-rk.g\"kb.-r\"x.v\'f.ezkh. d.-\'.vk.x-kwkqxj.s. bxb.vgslm.\'dx.x\'ga\"kz.hr\'.sa\'d-ka.-rk.qs,k.ym\"gg,\'qekzyb.,ksqhr\"gkj.m\"gg,\'qekz.m\"ggx.mgs k.sqa.-smkx.r\"x.v\'af.-\'.hsmsqasb.rk.\"x.vz\' er-.vkt\'zk.-rk.-z\"vsg.kgakzxj.zkwksg\"qe.r\"x.\"akq-\"-f.-\'.vk.q;usasms.sqa.lgs\",.-\'.-rk.-rz\'qkb.m\"gg,\'qekz.lrsggkqekx.-;lrsggs.-\'.z\"- sg.l\',vs-j.hrkzk.rk.m\"ggx.o z\"j.aktks-x.-;lrsggsj.sqa.r zgx.r\",.\'wkz.s.hs-kztsgg.-\'.r\"x.dzkx ,ka.aks-rb.m\"gg,\'qekz.\"qekx-x.-rk.rksz-nxrsdka.rkzv.sqa.\'zakzx.-rk.zkx-.\"ql\"qkzs-kaj.v -.qsm\"s.ki-zsl-x.\'qk.t\"zx-b.m\"gg,\'qekzj.x dd\'z-ka.vf.h;msv\".sqa.r\"x.sz,fj.dzkdszkx.-\'.a\"x-z\"v -k.xr\"d,kq-x.\'t.hsmsqasq.hksd\'qx.-\'.\'dkzs-\"wkx.sz\' qa.-rk.h\'zgab.qsm\"sj.xr z\"j.zs,\'qasj.sqa.z\'xx.tgkk.-\'.-rk.usvsz\".-z\"vk.t\'z.s\"ab.-rkf.t\"qa.s.l\',s-\'xk.-;lrsggsj.zkxl ka.vf.-rk.usvsz\".\"q.zkdsf,kq-.t\'z.xdsz\"qe.,;vsm ;x.g\"tkb.rksgka.vf.qsm\"s;x.rkzvj.-;lrsggs.zk- zqx.-\'.t\"er-.m\"gg,\'qekzj.hr\'.a\'qx.r\"x.\'hq.qsq\'-klr.x \"-j.x\",\"gsz.-\'.-;lrsggs;xb.h;msv\".sqa.r\"x.sz,f.t\"er-.xr z\"j.qsm\"sj.sqa.-rk.a\'zs.,\"gsukj.hr\"gk.z\'xx.zk,\'-kgf.d\"g\'-x.s.uk-.sqa.xr\'\'-x.a\'hq.dgsqkx.lszzf\"qe.-rk.w\"vzsq\" ,.hksd\'qxb.,;vsm .sqa.-rk.usvsz\".szz\"wk.-\'.zk\"qt\'zlk.-;lrsggsb.l\'qtz\'q-ka.vf.\'m\'fkj.h;msv\".sqa.r\"x.sz,f.x-sqa.a\'hqb.t\"er-\"qe.\"q.hsmsqas;x.w\"vzsq\" ,.,\"qkj.-;lrsggs.a\"xz d-x.m\"gg,\'qekz;x.x \"-.sqa.x-svx.r\",b.m\"gg,\'qekz.zkt xkx.-\'.vk.rksgkaj.lr\'\'x\"qe.-\'.a\"k.s.tzkk.,sq.zs-rkz.-rsq.vk.\"qlszlkzs-kap.-;lrsggs.-smkx.r\",.-\'.-rk.hs-kztsgg.hrkzk.-rkf.t\' er-j.hrkzk.m\"gg,\'qekz.a\"kx.dkslkt ggfb.-;lrsggs.kx-svg\"xrkx.sq.\' -zkslr.lkq-kz.s-.-rk.v \"ga\"qe.hrkzk.q;u\'v .a\"kaj.-\'.vk.z q.vf.qsm\"s.sqa.xr z\"b.\"q.s.,\"anlzka\"-x.xlkqkj.-;lrsggs.sddkszx.vkt\'zk.-rk. q\"-ka.qs-\"\'qx.-\'.zkwksg.hsmsqas;x.-z k.qs- zk.-\'.-rk.h\'zgab.\"q.s.d\'x-nlzka\"-x.xlkqkj.xr z\".rkgdx.v lmf.vszqkx.h\"-r.r\"x.zkl\'wkzfb";

        wordCount(text);
        

        //System.out.println(output);

        String output = "";

        for(int i = 0; i < text.length(); i++){
            String letter = text.substring(i, i+1);
            if(!sub.get(letter).equals("")){
                output += sub.get( text.substring(i, i+1) );
            } else {
                output += "~";
            }
            
        }
        

        System.out.println(output);
    }

    public static void wordCount(String text){
        Hashtable<String, Integer> count = new Hashtable<String, Integer>();
        //String text = ".sa mple.sampl.sa m.samp.";
        String output = "";
        String pattern = "\\.[^.]*";
        //String pattern = "\\.[abcdefghijklmnopqrstuvwxyz \\\"\',-;]+?\\.";

        System.out.println(pattern + "\n");

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);

		while (m.find()) {
            String word = m.group();
			count.putIfAbsent(word, 0); 
            count.put( word, count.get( word )+1 );
			//System.out.println(m.group());
		}


        ArrayList<String> word = new ArrayList<String>();
        ArrayList<Integer> wordCount = new ArrayList<Integer>();
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            word.add(entry.getKey());
            wordCount.add(entry.getValue());
        }

        for(int i = 0; i < wordCount.size()-1; i++){
            for(int j = i+1; j < wordCount.size(); j++){
                if(wordCount.get(j) > wordCount.get(i)){
                    String temp1 = word.get(i);
                    Integer temp2 = wordCount.get(i);
                    word.set(i, word.get(j));
                    wordCount.set(i, wordCount.get(j));
                    word.set(j, temp1);
                    wordCount.set(j, temp2);
                }
            }
        }

        for(int i = 0; i < word.size(); i++){
            System.out.println(word.get(i) + ": " + wordCount.get(i));
        }
    }
}