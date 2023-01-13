import java.util.HashMap;
import java.util.List;

public class TrieSet<T extends List<U>, U> {
    private final TrieSetNode<T,U> root;
}

class TrieSetNode<T extends List<U>,U> {
    private boolean canBeEnd;
    private final HashMap<U, TrieSetNode<T,U>> nodes;

    public TrieSetNode() {
        this.canBeEnd = false;
        this.nodes = new HashMap<>();
    }

    public void makeNodeFinal() {
        this.canBeEnd = true;
    }

    public void removeFinalNode() {
        this.canBeEnd = false;
    }

    public void addElementToNode(T elementToAdd) {
        if (elementToAdd.isEmpty()) {
            this.makeNodeFinal();
            return;
        }

        U firstElement = elementToAdd.get(0);
        T remainingElements = (T)elementToAdd.subList(1, elementToAdd.size());

        if(!this.nodes.containsKey(firstElement)) {
            this.nodes.put(firstElement, new TrieSetNode<>());
        }

        this.nodes.get(firstElement).addElementToNode(remainingElements);
    }

    public int checkIfElementExistsPartially(T elementToRetrieve) {
        //Negative numbers are used for hits. Positive or 0 numbers always means there was a miss by size()

        if (elementToRetrieve.isEmpty()) {
            if (!this.canBeEnd) return 0;
            else                return -1; // Hit
        }

        U firstElement = elementToRetrieve.get(0);
        T remainingElements = (T)elementToRetrieve.subList(1, elementToRetrieve.size());

        if (!this.nodes.containsKey(firstElement)) return elementToRetrieve.size();

        return this.nodes.get(firstElement).checkIfElementExistsPartially(remainingElements);
    }
}