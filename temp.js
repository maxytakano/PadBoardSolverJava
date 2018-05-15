class Trie {
  constructor() {
    this.root = new Node();
  }

  getRoot() {
    return this.root;
  }

  insert(word) {
    let curNode = this.root;
    for (let i = 0; i < word.length; i++) {
      const charCode = word.charCodeAt(i) - 97;
      if (!curNode.children[charCode]) {
        curNode.children[charCode] = new Node();
      }
      curNode = curNode.children[charCode];
    }
    curNode.isEnd = true;
  }

  search(word) {
    let curNode = this.root;
    for (let i = 0; i < word.length; i++) {
      const charCode = word.charCodeAt(i) - 97;
      if (!curNode.children[charCode]) {
        return false;
      }
      curNode = curNode.children[charCode];
    }
    return curNode.isEnd;
  }

  isPrefix(word) {
    let curNode = this.root;
    for (let i = 0; i < word.length; i++) {
      const charCode = word.charCodeAt(i) - 97;
      if (!curNode.children[charCode]) {
        return false;
      }
      curNode = curNode.children[charCode];
    }
    return true;
  }
}

class Node {
  constructor() {
    this.children = [];
    this.isEnd = false;
  }
}


function boggle(board, dictionary) {
  const boardLength = board.length;
  const visited = [];
  const trie = new Trie();
  for (let i = 0; i < board.length; i++) {
    visited[i] = [];
    for (let j = 0; j < board[0].length; j++) {
      visited[i][j] = false;
    }
  }

  for (let word of dictionary) {
    trie.insert(word);
  }

  for (let row = 0; row < board.length; row++) {
    for (let col = 0; col < board[0].length; col++) {
      const charCode = board[row][col].charCodeAt(0) - 97;
      const child = trie.getRoot().children[charCode];
      if (child) {
        searchWord(child, board, row, col, visited, board[row][col]);
      }
    }
  }
}

function searchWord(node, board, row, col, visited, string) {
  if (node.isEnd) {
    console.log('is on board', string);
  }

  if (isValid(row, col, visited)) {
    visited[row][col] = true;
    
  }


}
