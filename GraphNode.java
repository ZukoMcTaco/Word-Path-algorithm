 public class GraphNode {

    // A node holds a String label, and an array of its neighbours
    public String label;
    public String[] neighbours = new String[6]; // 6 neighbours to start with
    public int limit = 6;
    public int currIndex = 0;

    // You may add further fields and methods. 
     
    public GraphNode(String label) {
      this.label = label;
    }

    public void addNeighbour(String s) {
       // add your code here
       if (this.currIndex<this.limit){
         this.neighbours[currIndex]=s;
         this.currIndex++;
       }
       else{
         resizeNeighbours();
         this.neighbours[currIndex]=s;
       }
   }
   public void resizeNeighbours(){
      int newLimit =2*this.limit;
      String [] newNeightbours = new String[newLimit];
      for (int i=0;i<this.limit;i++){
         newNeightbours[i]=this.neighbours[i];
      }
      this.neighbours = newNeightbours;
      this.limit = newLimit;
   }
   public void output(){
      for (int i= 0; i < this.neighbours.length; i++) {
         System.out.println(this.neighbours[i]);
      }
   }
   public int getLimit(){
      return this.limit;
   }

   public String getLabel(){
      return this.label;
   }
   public String[] getNeighbours(){
      return this.neighbours;
   }

}
