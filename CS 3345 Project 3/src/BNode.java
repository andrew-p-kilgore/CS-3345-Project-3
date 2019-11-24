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
			if(keys[i] != null) {
				if(key < keys[i])
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
	
	void connectchild( BNode child , int index ) {
		children[index] = child;
		if(child != null)
			child.parentnode = this;
	}
	
	BNode disconnectchild( int index ) {
		BNode tempNode = children[index];
		children[index] = null;
		return tempNode;
	}
	
	Integer simpleremovekey() {
		Integer tempNode = keys[numkeys-1];
		keys[numkeys-1] = null;
		numkeys--;
		return tempNode;
	}
	
	Integer simplefindakey(Integer key) {
		int index = 0;
		while ( (index < numkeys) && (keys [index] < key))
			index++;
		
		return index;
	}
	
	void removekey (Integer key) {
		int index = simplefindakey(key);
		
		if ( (index < numkeys) && (keys[index] == key) ) {
			if (children[0] == null)
				removekey1(index);
			else
				removekey2(index);
		}
		else {
			if (children[index].numkeys < 2)
				fillNode(index);
			
				children[index].removekey(key);
		}
	}
	
	void removekey1 (int index) {
		System.out.println("removekey1 called");
		System.out.println("Node before removal: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
		for (int i=(index + 1); i<numkeys; i++)
			keys[i-1] = keys[i];
		
		numkeys--;
		System.out.println("Node after removal: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
	}
	
	void removekey2 (int index) {
		System.out.println("removekey2 called");
		System.out.println("Node before removal: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
		Integer key = keys[index];
		
		if (children[index].numkeys >= 2) {
			Integer parent = getParent(index);
			keys[index] = parent;
			children[index].removekey(parent);
		}
		else if (children[index+1].numkeys >= 2) {
			Integer child = getChild(index);
			keys[index] = child;
			children[index+1].removekey(child);
		}
		else
		{
			combineNodes(index);
			children[index].removekey(key);
		}
		System.out.println("Node after removal: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
		
	}
	
	Integer getChild (int index) {
		BNode node = children [index + 1];
		while (node.children[0] != null)
			node = node.children[0];
		
		return node.keys[0];
	}
	
	Integer getParent (int index) {
		BNode node = children [index];
		while (node.children[0] != null)
			node = node.children[node.numkeys];
		
		return node.keys[node.numkeys-1];
	}
	
	void combineNodes ( int index ) {
		System.out.println("combineNodes called");
		BNode childnode = children[index];
		BNode siblingnode = children[index+1];
		childnode.keys[1] = keys[index];
		
		System.out.println("Node before combining: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
		for (int i=0; i<siblingnode.numkeys; i++)
			childnode.keys[2+i] = siblingnode.keys[i];
		
		if (childnode.children[0] != null) {
			for(int i=0; i<=siblingnode.numkeys; i++)
				childnode.children[2+i] = siblingnode.children[i];
		}
		
		for (int i=(index+1); i < numkeys; i++)
			keys[i-1] = keys[i];
		
		for (int i=(index+2); i <= numkeys; i++)
			children[i-1] = children[i];
		
		childnode.numkeys += (siblingnode.numkeys + 1);
		numkeys--;
		System.out.println("Node after combining: ");
		for(int i=0; i<numkeys; i++) {
			if(keys[i] != null)
				System.out.print(" [" + keys[i] + "] ");
		}
		System.out.print('\n');
	}
	
	void takekeyfromparent(int index) {
		BNode childnode = children[index];
		BNode siblingnode = children[index-1];
		System.out.println("takekeyfromparent called");
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
	
	void takekeyfromchild (int index) {
		BNode childnode = children[index];
		BNode siblingnode = children[index+1];
		System.out.println("takekeyfromchild called");
		childnode.keys[childnode.numkeys] = keys[index];
		keys[index] = siblingnode.keys[0];
		
		if (childnode.children[0] != null)
			childnode.children[childnode.numkeys+1] = siblingnode.children[0];
		
		for (int i=1; i<siblingnode.numkeys; i++)
			siblingnode.keys[i-1] = siblingnode.keys[i];
		
		if (siblingnode.children[0] != null) 
			for(int i=1; i<=siblingnode.numkeys; i++)
				siblingnode.children[i-1] = siblingnode.children[i];
		
		childnode.numkeys++;
		siblingnode.numkeys--;
	}
	
	void fillNode (int index) {
		if((index != 0) && (children[index-1].numkeys >= 2))
			takekeyfromparent(index);
		else if ((index != numkeys) && (children[index+1].numkeys >= 2))
			takekeyfromchild(index);
		else {
			if (index != numkeys)
				combineNodes(index);
			else
				combineNodes(index-1);
		}
	}
	
	
}
