public class BNode {

	Integer[] keys = new Integer [3];
	int numkeys = 0;
	BNode[] children = new BNode [4];
	//int numchildren = 0;
	BNode parentnode;
	
	BNode () {}
	
	int insertkeysintonode( Integer key ) {
		
		numkeys++;
		for( int i = (numkeys - 1); i >= 0; i-- ) {
			if(keys[i] == null)
				continue;
			else {
				if(key < this.keys[i])
					keys[i+1] = keys[i];
				else { 
					keys[i+1] = key;
					return i+1;
				}
			}
		}
		keys[0] = key;
		
		return 0;
	}
	
	void connectchild( int num, BNode child) {
		children[num] = child;
		if(child != null)
			child.parentnode = this;
	}
	
	BNode disconnectchild( int num ) {
		BNode temp = children[num];
		children[num] = null;
		return temp;
	}
	
	Integer simpleremovekey() {
		Integer temp = keys[numkeys-1];
		keys[numkeys-1] = null;
		numkeys--;
		return temp;
	}
	
	Integer findakey(int key) {
		int index = 0;
		while ( index < numkeys && keys [index] < key)
			index++;
		
		return index;
	}
	
	void removekey (int key) {}
	
	void removekey1 (int index) {
		for (int i=(index + 1); i<numkeys; i++)
			keys[i-1] = keys[i];
		
		numkeys--;
	}
	
	void removekey2 (int index) {}
	
	Integer getChild (int index) {
		BNode node = children [index + 1];
		while (node.children[0] == null)
			node = node.children[0];
		
		return node.keys[0];
	}
	
	Integer getParent (int index) {
		BNode node = children [index];
		while (node.children[0] == null)
			node = node.children[node.numkeys];
		
		return node.keys[node.numkeys-1];
	}
	
	void merge ( int index ) {
		BNode childnode = children[index];
		BNode siblingnode = children[index];
		
		childnode.keys[1] = keys[index];
		
		for (int i=0; i<siblingnode.numkeys; i++)
			childnode.keys[i+2] = siblingnode.keys[i];
		
		if (childnode.children[0] != null) {
			for(int i=0; i<=siblingnode.numkeys; i++)
				childnode.children[i+2] = siblingnode.children[i];
		}
		
		for (int i=(index+1); i < numkeys; i++)
			keys[i-1] = keys[i];
		
		for (int i=(index+2); i <= numkeys; i++)
			children[i-1] = children[i];
		
		childnode.numkeys += siblingnode.numkeys + 1;
		numkeys--;
	}
	
	void borrowkeyfromparent(int index) {
		BNode childnode = children[index];
		BNode siblingnode = children[index-1];
		
		for (int i=(childnode.numkeys-1); i>=0; i--)
			childnode.keys[i+1] = childnode.keys[i];
		
		if (childnode.children[0] != null) {
			for(int i=childnode.numkeys; i>=0; i--)
				childnode.children[i+1] = childnode.children[i];
		}
		
		childnode.keys[0] = keys[index-1];
		
		if(childnode.children[0] != null)
			childnode.children[0] = siblingnode.children[siblingnode.numkeys];
		
		keys[index-1] = siblingnode.keys[siblingnode.numkeys-1];
		
		childnode.numkeys++;
		siblingnode.numkeys--;
	}
	
	void borrowfromchild (int index) {
		BNode childnode = children[index];
		BNode siblingnode = children[index+1];
		
		childnode.keys[childnode.numkeys] = keys[index];
		
		if (childnode.children[0] != null)
			childnode.children[childnode.numkeys+1] = siblingnode.children[0];
		
		keys[index] = siblingnode.keys[0];
		
		for (int i=1; i<siblingnode.numkeys; i++)
			siblingnode.keys[i-1] = siblingnode.keys[i];
		
		if (siblingnode.children[0] != null) {
			for(int i=1; i<=siblingnode.numkeys; i++)
				siblingnode.children[i-1] = siblingnode.children[i];
		}
		
		childnode.numkeys++;
		siblingnode.numkeys--;
	}
}
