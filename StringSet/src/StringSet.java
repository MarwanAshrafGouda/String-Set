import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class StringSet implements StringSetInterface {

    private ArrayList<Integer> B = new ArrayList<>();

    public ArrayList<Integer> getB() {
        return B;
    }

    public StringSet(int size){
        int s = size/31 + (size%31==0?0:1);
        for(int i=0; i<s; i++){
            this.B.add(0);
        }
    }

    public void fillSet(int n){
        this.B.set(n/31, this.B.get(n/31)|(int)Math.pow(2,n%31));
    }

    @Override
    public void union(StringSet S, LinkedHashMap<String, Integer> U) {
        String printer = "{";
        for(int i=0; i<getB().size(); i++){
            int tempInt = this.getB().get(i) | S.getB().get(i);
            int count = 0;
            while(tempInt>0) {
                if (tempInt % 2 == 1) {
                    printer += U.keySet().toArray()[count + i * 31] + ", ";
                }
                count++;
                tempInt = tempInt >> 1;
            }
        }
        printer += "}";
        printer = printer.replaceAll(", }","}");
        System.out.println(printer);
    }

    @Override
    public void intersect(StringSet S, LinkedHashMap<String, Integer> U) {
        String printer = "{";
        for(int i=0; i<getB().size(); i++){
            int tempInt = this.getB().get(i) & S.getB().get(i);
            int count = 0;
            while(tempInt>0) {
                if (tempInt % 2 == 1) {
                    printer += U.keySet().toArray()[count + i * 31] + ", ";
                }
                count++;
                tempInt = tempInt >> 1;
            }
        }
        printer += "}";
        printer = printer.replaceAll(", }","}");
        System.out.println(printer);
    }

    @Override
    public void complement(LinkedHashMap<String, Integer> U) {
        String printer = "{";
        for(int i=0; i<getB().size(); i++){
            int tempInt = this.getB().get(i);
            int count = 0;
            for(int j=0; j<31; j++) {
                if ((tempInt % 2 == 0) && (count + i * 31 < U.keySet().size())) {
                    printer += U.keySet().toArray()[count + i * 31] + ", ";
                }
                count++;
                tempInt = tempInt >> 1;
            }
        }
        printer += "}";
        printer = printer.replaceAll(", }","}");
        System.out.println(printer);
    }

    public static void main(String[] args) {

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        ArrayList<StringSet> sets = new ArrayList<>();
        String alphabet = new String();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the universe elements, each on a new line, enter -1 to stop");
        int counter = 0;
        String uni = scan.nextLine();
        while(!uni.equals("-1")){
            if(map.containsKey(uni)) {
                uni = scan.nextLine();
                continue;
            }
            map.put(uni,counter++);
            uni = scan.nextLine();
        }
        counter = 0;
        while (true) {
            System.out.println("Pick the number of your desired operation\n1) Create a new set\n2) View set elements\n3) Union of two sets\n4) Intersection between two sets\n5) Complement of a set\n6) Exit\n******************************************************");
            int choice = scan.nextInt();
            switch(choice){
                case 1:
                    char c = (char) ('A' + counter++);
                    alphabet += c;
                    System.out.println("Enter the elements of set "+c+", each on a new line.\nMake sure they belong to the Universe.\nEnter -1 to stop");
                    StringSet temp = new StringSet(map.size());
                    uni = scan.nextLine();
                    while(!uni.equals("-1")){
                        if(map.containsKey(uni)){
                            temp.fillSet(map.get(uni));
                        }
                        uni = scan.nextLine();
                    }
                    sets.add(temp);
                    break;
                case 2:
                    System.out.print("Enter the set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    String set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    ArrayList<Integer> setVal = sets.get((set.toUpperCase().charAt(0)-'A')).getB();
                    String printer = "{";
                    for(int i=0; i<setVal.size(); i++){
                        int tempInt = setVal.get(i);
                        int count = 0;
                        while(tempInt>0){
                            if(tempInt%2 == 1){
                                printer += map.keySet().toArray()[count+i*31] + ", ";
                            }
                            count++;
                            tempInt = tempInt >> 1;
                        }
                    }
                    printer += "}";
                    printer = printer.replaceAll(", }","}");
                    System.out.println(printer);
                    break;
                case 3:
                    System.out.print("Enter the first set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    StringSet firstUnion = sets.get((set.toUpperCase().charAt(0)-'A'));
                    System.out.print("Enter the second set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    StringSet secondUnion = sets.get((set.toUpperCase().charAt(0)-'A'));
                    firstUnion.union(secondUnion,map);
                    break;
                case 4:
                    System.out.print("Enter the first set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    StringSet firstInter = sets.get((set.toUpperCase().charAt(0)-'A'));
                    System.out.print("Enter the second set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    StringSet secondInter = sets.get((set.toUpperCase().charAt(0)-'A'));
                    firstInter.intersect(secondInter,map);
                    break;
                case 5:
                    System.out.print("Enter the set name\nAvailable sets: ");
                    for(int i=0;i<alphabet.length();i++)
                        System.out.print(alphabet.charAt(i) + " ");
                    System.out.println();
                    set = scan.next();
                    while(!alphabet.contains(set.toUpperCase())){
                        System.out.println("This set has not been created yet");
                        set = scan.next();
                    }
                    StringSet complemented = sets.get((set.toUpperCase().charAt(0)-'A'));
                    complemented.complement(map);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

    }
}

