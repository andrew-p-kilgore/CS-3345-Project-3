public class BTree {

	BNode root = new BNode();
	int maxdegree = 4;
	int height;
	int numkeys;
	
	BTree () {}
	
	/*void addNode ( int key ) {
		root = insertNode(root, key);
	}*/
	
	void insertNode (Integer key) {
		
		BNode node = root;
		Integer temp = key;
		
		while(true) {
			if(node.numkeys == 3) {
				split(node);
				node = node.parentnode;
				node = getnextchild(node,key);
			}
			else if( node.children[0] == null ) {
				break;
			}
			else
				node = getnextchild(node, key);
		}
		node.insertkeysintonode(temp);
	}
	
	void split(BNode node) {
		Integer itemB, itemC;
		BNode parent, child2, child3;
		int itemIndex;
		BNode newRight = new BNode();
		
		itemC = node.simpleremovekey();
		itemB = node.simpleremovekey();
		child2 = node.disconnectchild(2);
		child3 = node.disconnectchild(3);
		
		if(node == root) {
			root = new BNode();
			parent = root;
			root.connectchild(0, node);
		}
		else
			parent = node.parentnode;
		
		itemIndex = parent.insertkeysintonode(itemB);
		int n = parent.numkeys;
		
		for(int i=n-1; i>itemIndex; i--) {
			BNode temp = parent.disconnectchild(i);
			parent.connectchild(i+1, temp);
		}
		parent.connectchild(itemIndex+1, newRight);
		
		newRight.insertkeysintonode(itemC);
		newRight.connectchild(0, child2);
		newRight.connectchild(1, child3);
	}
	
	BNode getnextchild(BNode node, Integer key) {
		int i;
		for(i=0; i<node.numkeys; i++) {
			if( key < node.keys[i] )
				return node.children[i];
		}
		return node.children[i];
	}
	
	void showTree() { recursivelyshowTree( root, 0, 0); }
	
	void recursivelyshowTree(BNode node, int level, int childnum) {
		
		if( level == 0 )
			System.out.print("Root ");
		else {
			System.out.println("Level: " + level + " Child: " + childnum + " ");
			System.out.println("Parent's Left-Most Key: " + node.parentnode.keys[0]);
		}
		System.out.print("Keys:");
		for(int i=0; i<node.keys.length; i++) {
			if(node.keys[i] != null)
				System.out.print(" [" + node.keys[i] + "] ");
		}
		System.out.print("\n\n");
		
		for(int i=0; i < (node.numkeys + 1); i++) {
			BNode nextNode = node.children[i];
			if(nextNode != null)
				recursivelyshowTree(nextNode, (level + 1), i);
			else
				return;
		}
	}
}
