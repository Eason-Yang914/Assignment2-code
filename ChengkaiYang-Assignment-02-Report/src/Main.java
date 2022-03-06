import java.io.*;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeArray.sort;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static int index = 1;
    public static ArrayList<Word> words = new ArrayList<>();
    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<String> speeches = new ArrayList<>();
    public static int keywordNumber = 0;
    public static int definitionNumber = 0;

    /**
     * Show the command line.
     */
    public void showCommandLine(){
        System.out.print("Search ["+ index++ +"]: ");
    }

    /**
     * Show the help message
     */
    public void showHelp(){
        System.out.println("\t|");
        System.out.println("\tPARAMETER HOW-TO, please enter:\n" +
                "\t1. A search key -then 2. An optional part of speech -then\n" +
                "\t3. An optional 'distinct' -then 4. An optional 'reverse'");
        System.out.println("\t|");
    }

    /**
     * If not find the word ,use this method
     */
    public void showNotFind(){
        System.out.println("\t|\n" +
                "<NOT FOUND> To be considered for the next release. Thank you.\n" +
                "|");
        showHelp();
    }

    /**
     * Show the list not has the word.
     * @param index
     * @param word
     */
    public void showNotHasWord(int index,String word){
        String order = "";
        if(index==2){
            order = "2nd";
            System.out.printf("\t|\n" +
                    "\t<The entered %s parameter '%s' is NOT a part of speech.>\n" +
                    "\t<The entered %s parameter '%s' is NOT 'distinct'.>\n" +
                    "\t<The entered %s parameter '%s' is NOT 'reverse'.>\n" +
                    "\t<The entered %s parameter '%s' was disregarded.>\n" +
                    "\t<The %s parameter should be a part of speech or 'distinct' or 'reverse'.>\n" +
                    "\t|\n", order,word, order,word,order,word,order,word,order);
        }
        else if(index ==3) {
            order = "3rd";
            System.out.printf("\t|\n" +
                    "\t<The entered %s parameter '%s' is NOT 'distinct'.>\n" +
                    "\t<The entered %s parameter '%s' is NOT 'reverse'.>\n" +
                    "\t<The entered %s parameter '%s' was disregarded.>\n" +
                    "\t<The %s parameter should be a part of speech or 'distinct' or 'reverse'.>\n" +
                    "\t|\n", order,word, order,word,order,word,order);
        }
        else if (index == 4){
            order = "4th";
            System.out.printf("\t|\n" +
                    "\t<The entered %s parameter '%s' is NOT 'reverse'.>\n" +
                    "\t<The entered %s parameter '%s' was disregarded.>\n" +
                    "\t<The %s parameter should be a part of speech or 'distinct' or 'reverse'.>\n" +
                    "\t|\n", order,word, order,word,order);
        }

    }

    /**
     * Show the list of the word
     * @param list
     */
    public void showList(List<Word> list){

        if(list.isEmpty()){
            System.out.print("\t|\r\n\t<NOT FOUND> To be considered for the next release. Thank you.\r\n\t|\r\n");
            showHelp();
        }else{
            System.out.println("\t|");
            for(Word w:list){
                System.out.println("\t"+w);
            }
            System.out.println("\t|");
        }
    }
    /**
     *
     * If the speeches has the word or the word is 'reverse' or 'distinct'
     * return true, else return false
     * @param word
     * @return
     */
    public boolean hasWord(String word){
        word = word.toLowerCase();
        if(speeches.contains(word) || word.equalsIgnoreCase("reverse") || word.equalsIgnoreCase("distinct"))
            return true;
        return false;
    }
    /**
     * Get the list that keyword is key
     * @param key
     * @return a list
     */
    public ArrayList<Word> getEnumList(String key){
        ArrayList<Word> list = new ArrayList<>();
        for(Word word : Word.values()){
            String keyword = word.getKeyword();
            if(keyword.equalsIgnoreCase(key)){
                list.add(word);
            }
        }
        return list;
    }

    /**
     * Get the list that keyword is key and speech is "s"
     * @param key
     * @return a list
     */
    public ArrayList<Word> getEnumList(String key,String s){
        ArrayList<Word> list = new ArrayList<>();
        for(Word word : Word.values()){
            String keyword = word.getKeyword();
            String speech = word.getSpeech();
            if(keyword.equalsIgnoreCase(key)  && speech.equalsIgnoreCase(s)){
                list.add(word);
            }
        }
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        return list;
    }

    /**
     * Return the distinct list
     * @return
     */
    public ArrayList<Word> getListDistinctList(ArrayList<Word> list){
        ArrayList<Word> distinctList = new ArrayList<>();
        ArrayList<String> distincts = new ArrayList<>();
        for(Word word:list){
            String distinct = word.getDefinition();
            if(!distincts.contains(distinct)){
                distincts.add(distinct);
                distinctList.add(word);
            }
        }
        return distinctList;
    }

    public void dealCommand(String[] commands){
        if(commands == null)
            showHelp();
        else{
            String first = commands[0].toLowerCase();
            if(first.equalsIgnoreCase("!help"))
                showHelp();
            else if(first.equalsIgnoreCase("!q")){
                System.out.println("-----THANK YOU-----");
                System.exit(0);
            }
            else if(!keywords.contains(first)){
                showNotFind();
            }else if(commands.length>=5){
                showHelp();
            }else if(commands.length == 1){
                ArrayList<Word> wordList = getEnumList(first);
                showList(wordList);
            }else if(commands.length>=2){
                String second = "";
                String third = "";
                String forth = "";
                ArrayList<Word> list = getEnumList(commands[0]);
                if(commands.length == 2){
                    second = commands[1].toLowerCase();
                    if(!hasWord(second)){
                        showNotHasWord(2,second);
                        showList(list);
                    }else{
                        if(speeches.contains(second)){
                            list = getEnumList(commands[0],second);
                            showList(list);
                        }else if(second.equalsIgnoreCase("distinct")){
                            ArrayList<Word> distinctList = getListDistinctList(list);
                            showList(distinctList);
                        }else if(second.equalsIgnoreCase("reverse")){
                            Collections.reverse(list);
                            showList(list);
                        }
                    }
                }
                else if(commands.length == 3){
                    second = commands[1].toLowerCase();
                    if(!hasWord(second)){
                        showNotHasWord(2,second);
                    }
                    third = commands[2].toLowerCase();
                    if(!third.equalsIgnoreCase("reverse") && !third.equalsIgnoreCase("distinct")){
                        showNotHasWord(3,third);
                    }
                        if(second.equalsIgnoreCase("distinct") && third.equalsIgnoreCase("reverse")){
//                            ArrayList<Word> list0 = getEnumList(first);
                            ArrayList<Word> list1 = getListDistinctList(list);
                            Collections.reverse(list1);
                            showList(list1);
                        }else if(speeches.contains(second)){
                            ArrayList<Word> list0 = getEnumList(first,second);
                            if(third.equalsIgnoreCase("reverse")){
                                Collections.reverse(list0);
                                showList(list0);
                            }else if(third.equalsIgnoreCase("distinct")){
                                ArrayList<Word> list1 = getListDistinctList(list0);
                                showList(list1);
                            }else{
                                showList(list0);
                            }
                        }
                }else if(commands.length == 4){
                    second = commands[1].toLowerCase();
                    if(!hasWord(second))
                        showNotHasWord(2,second);
                    third = commands[2].toLowerCase();
                    if(!third.equalsIgnoreCase("reverse") && !third.equalsIgnoreCase("distinct"))
                        showNotHasWord(3,third);
                    forth = commands[3].toLowerCase();
                    if(!forth.equalsIgnoreCase("reverse")){
                        showNotHasWord(4,forth);
                    }
                    if(speeches.contains(second)){
                        list = getEnumList(first,second);
                        ArrayList<Word> list1 = null;
                        if(third.equalsIgnoreCase("distinct")){
                            list = getListDistinctList(list);
                            if(forth.equalsIgnoreCase("reverse")){
                                Collections.reverse(list);
                            }
                        }else if(third.equalsIgnoreCase("reverse")){
                            Collections.reverse(list);
                            if(forth.equalsIgnoreCase("distinct")){
                                list = getListDistinctList(list);
                            }
                        }
                        showList(list);
                    }else if(second.equalsIgnoreCase("reverse")){

                        Collections.reverse(list);
                        ArrayList<Word> list1 = null;
                        if(third.equalsIgnoreCase("distinct")){
                            list1 = getListDistinctList(list);
                            showList(list1);
                        }else{
                            showList(list);
                        }

                    }else if(second.equalsIgnoreCase("distinct")){
                        list = getListDistinctList(list);
                        ArrayList<Word> list1 = null;
                        if(third.equalsIgnoreCase("reverse")){
                            Collections.reverse(list);
                            showList(list);
                        }

                    }else{
                        showList(list);
                    }
                }

                else{
                    ArrayList<Word> wordList = getEnumList(first);
                }

            }
        }
    }

    /**
     * Get the lowercase of the command words
     * @return
     */
    public String[] getWordLowercase(){
        String line = scanner.nextLine().trim().toLowerCase();
        if(line.equalsIgnoreCase("!Q")){
            System.out.println("-----THANK YOU-----");
            System.exit(0);
        }

        if(line == null || line.equals(""))
            return null;
        String[] array = line.split(" ");
        return array;
    }

    /**
     * Load data
     */
    public void loadData(){
        System.out.println("! Loading data...");
        File file = new File("data.txt");
        try {
            BufferedReader bufr = new BufferedReader(new FileReader(file));
            String line = bufr.readLine();
            while(line!=null){
                String[] array = line.split(";");
                if(array.length == 3){
                    addWordEnum(array[0],array[1],array[2]);
                }
                line = bufr.readLine();
            }
            bufr.close();

            System.out.println("! Loading completed...\n");
            for(Word word : Word.values()){
                String keyword = word.getKeyword().toLowerCase();
                String speech = word.getSpeech().toLowerCase();
                if(!keywords.contains(keyword))
                    keywords.add(keyword);
                if(!speeches.contains(speech))
                    speeches.add(speech);
            }
            System.out.println("===== DICTIONARY 340 JAVA =====");
            keywordNumber = keywords.size();
            System.out.println("----- Keywords: "+ keywordNumber);
            definitionNumber = Word.values().length;
            System.out.println("----- Definitions: " + definitionNumber+"\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main(){
        loadData();
        while(true){
            showCommandLine();
            String[] array = getWordLowercase();
            dealCommand(array);
        }

    }

    public void addWordEnum(String keyword,String speech,String definition){
        DynamicEnumicEnumUtil.addEnum(Word.class, keyword, new Class<?>[]{String.class, String.class, String.class}, new Object[]{keyword, speech, definition});
    }

    public static void main(String[] args){
        new Main();
    }

}
