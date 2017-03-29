package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	   
		if(word==null)
			return false;
		TrieNode curr = root;
		String l = word.toLowerCase();
		for(int i =0;i<l.length();i++){
			char c = l.charAt(i);
			if(curr.getValidNextCharacters().contains(c))
				curr = curr.getChild(c);
			else 
				curr = curr.insert(c);
			
		}
		if(!curr.endsWord()){
			curr.setEndsWord(true);
			size++;
			return true;
		}
		
		
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    String l = s.toLowerCase();
	    TrieNode curr= root;
	    for(int i = 0;i<l.length();i++){
	    	char c = l.charAt(i);
	    	if(curr.getValidNextCharacters().contains(c))
	    		curr = curr.getChild(c);
	    	else 
	    		return false;
	    	
	    }
	    if(curr.endsWord())
	    	return true;
	    
		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> res = new LinkedList<String>();
    	 if(prefix==null)
    		 return null;
    	 String l = prefix.toLowerCase();
    	 TrieNode curr = root;
    	 for(int i=0;i<l.length();i++){
    		 char c = l.charAt(i);
    		 if(curr.getValidNextCharacters().contains(c))
    			 curr = curr.getChild(c);
    		 else
    			 return res;
    		 
    	 }
    	 int count = 0;
    	 if(curr.endsWord()){
    		 res.add(curr.getText());
    		 count++;
    		 
    	 }
    	 List<TrieNode> nqueue = new LinkedList<TrieNode>();
    	 List<Character> childs = new LinkedList<Character>(curr.getValidNextCharacters());
    	 for(int i=0;i<childs.size();i++){
    		 char c = childs.get(i);
    		 nqueue.add(curr.getChild(c));
    	 }
    	 while(!nqueue.isEmpty()&&count<numCompletions){
    		 curr = nqueue.remove(0);
    		 if(curr.endsWord())
    		 {
    			 res.add(curr.getText());
    			 count++;
    		 }
    		 List<Character> cs = new LinkedList<Character>(curr.getValidNextCharacters());
    		 for(int i=0;i<cs.size();i++){
    			 char c = cs.get(i);
    			 nqueue.add(curr.getChild(c));
    		 }
    	 }
    	 
         return res;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}