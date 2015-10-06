package graph;

/**
 * Created by DYKim on 15. 10. 6..
 */
public class Vertex {
    int number;
    int rank;
    Vertex parent;

    public Vertex(int number) {
        this.number = number;
        this.rank = -1;
        this.parent = null;
    }

    /*
        Setter
     */
    public void SetNumber(int number)       {   this.number = number;   }
    public void SetRank(int rank)           {   this.rank = rank;       }
    public void SetParent(Vertex parent)    {   this.parent = parent;   }

    /*
        Tool method
     */
    /**
     *  makeSet method
     */
    public void makeSet() {
        this.parent = this;
        this.rank = 0;
    }

    @Override
    public String toString() {
        String str = "(";
        str = str + number + "," + rank + ",";
        if (parent != null) {
            str = str + "Yes)";
        }
        else {
            str = str + "No)";
        }
        return str;
    }
}
