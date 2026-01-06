class Solution {
    // Given an array of strings strs, group the anagrams together. 
    // You can return the answer in any order.
 
    // Example 1:
    // Input: strs = ["eat","tea","tan","ate","nat","bat"]
    // Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

    // Explanation:
    // There is no string in strs that can be rearranged to form "bat".
    // The strings "nat" and "tan" are anagrams as they can be 
    // rearranged to form each other.
    // The strings "ate", "eat", and "tea" are anagrams as they 
    // can be rearranged to form each other.

    // Example 2:
    // Input: strs = [""]
    // Output: [[""]]

    // Example 3:
    // Input: strs = ["a"]
    // Output: [["a"]]

    // Constraints:
    // - 1 <= strs.length <= 104
    // - 0 <= strs[i].length <= 100
    // - strs[i] consists of lowercase English letters.
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> register = new HashMap<>();
        for (String s : strs) {
            char[] c = s.toCharArray();
            Arrays.sort(c);
            String key = new String(c);
            List<String> l = 
                register.getOrDefault(key, new ArrayList<>());
            l.add(s);
            register.put(key, l);
        }

        List<List<String>> result = new ArrayList<>();
        for (String key : register.keySet()) {
            result.add(register.get(key));
        }

        return result;
    }

    // Given an integer array nums and an integer k, 
    // return the k most frequent elements. You may return the answer in any order.

    // Example 1:
    // Input: nums = [1,1,1,2,2,3], k = 2
    // Output: [1,2]

    // Example 2:
    // Input: nums = [1], k = 1
    // Output: [1]

    // Example 3:
    // Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
    // Output: [1,2]

    // Constraints:
    // - 1 <= nums.length <= 105
    // - -104 <= nums[i] <= 104
    // - k is in the range [1, the number of unique elements in the array].
    // - It is guaranteed that the answer is unique.
    
    // Follow up: Your algorithm's time complexity must be better than O(n log n), 
    // where n is the array's size.
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(
            (a, b) -> freq.get(a) - freq.get(b)
        );

        for (int n : freq.keySet()) {
            queue.add(n);
            if (queue.size() > k) {
                queue.poll();
            }
        }

        int[] result = new int[k];
        int index = 0;
        while (!queue.isEmpty()) {
            result[index++] = queue.poll();
        }

        return result;
    }

    // Given an integer array nums, return an array answer such that 
    // answer[i] is equal to the product of all the elements of nums except nums[i].
    // The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
    // You must write an algorithm that runs in O(n) time and without using the division operation.

    // Example 1:
    // Input: nums = [1,2,3,4]
    // Output: [24,12,8,6]

    // Example 2:
    // Input: nums = [-1,1,0,-3,3]
    // Output: [0,0,9,0,0]
    

    // Constraints:
    // - 2 <= nums.length <= 105
    // - -30 <= nums[i] <= 30
    // - The input is generated such that answer[i] is guaranteed to fit in a 32-bit integer.

    // Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = 1;
        int prod = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result[i] = prod;
            prod *= nums[i];
        }

        prod = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; i--) {
            result[i] *= prod;
            prod *= nums[i];
        }

        return result;
    }

    // Determine if a 9 x 9 Sudoku board is valid. Only the filled 
    // cells need to be validated according to the following rules:

    // Each row must contain the digits 1-9 without repetition.
    // Each column must contain the digits 1-9 without repetition.
    // Each of the nine 3 x 3 sub-boxes of the grid must contain 
    // the digits 1-9 without repetition.
    
    // Note:
    // A Sudoku board (partially filled) could be valid but is not necessarily solvable.
    // Only the filled cells need to be validated according to the mentioned rules.
    

    // Example 1:
    // Input: board = 
    // [["5","3",".",".","7",".",".",".","."]
    // ,["6",".",".","1","9","5",".",".","."]
    // ,[".","9","8",".",".",".",".","6","."]
    // ,["8",".",".",".","6",".",".",".","3"]
    // ,["4",".",".","8",".","3",".",".","1"]
    // ,["7",".",".",".","2",".",".",".","6"]
    // ,[".","6",".",".",".",".","2","8","."]
    // ,[".",".",".","4","1","9",".",".","5"]
    // ,[".",".",".",".","8",".",".","7","9"]]
    // Output: true

    // Example 2:
    // Input: board = 
    // [["8","3",".",".","7",".",".",".","."]
    // ,["6",".",".","1","9","5",".",".","."]
    // ,[".","9","8",".",".",".",".","6","."]
    // ,["8",".",".",".","6",".",".",".","3"]
    // ,["4",".",".","8",".","3",".",".","1"]
    // ,["7",".",".",".","2",".",".",".","6"]
    // ,[".","6",".",".",".",".","2","8","."]
    // ,[".",".",".","4","1","9",".",".","5"]
    // ,[".",".",".",".","8",".",".","7","9"]]
    // Output: false
    // Explanation: Same as Example 1, except with the 5 in the top left 
    // corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
    

    // Constraints:
    // - board.length == 9
    // - board[i].length == 9
    // - board[i][j] is a digit 1-9 or '.'.
    public boolean isValidSudoku(char[][] board) {
        // check rows
        for (int row = 0; row < board.length; row++) {
            if (!checkRow(board, row)) {
                return false;
            }
        }

        // check columns
        for (int col = 0; col < board[0].length; col++) {
            if (!checkCol(board, col)) {
                return false;
            }
        }

        // check boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                if (!checkBox(board, boxRow, boxCol)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkRow(char[][] board, int row) {
        Set<Character> data = new HashSet<>();
        for (int col = 0; col < board[row].length; col++) {
            char c = board[row][col];
            if (c != '.' && !data.add(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkCol(char[][] board, int col) {
        Set<Character> data = new HashSet<>();
        for (int row = 0; row < board.length; row++) {
            char c = board[row][col];
            if (c != '.' && !data.add(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkBox(char[][] board, int boxRow, int boxCol) {
        Set<Character> data = new HashSet<>();
        int rowStart = 3 * boxRow, colStart = 3 * boxCol;

        for (int r = rowStart; r < rowStart + 3; r++) {
            for (int c = colStart; c < colStart + 3; c++) {
                char c_ = board[r][c];
                if (c_ != '.' && !data.add(c_)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Given an unsorted array of integers nums, return the 
    // length of the longest consecutive elements sequence.

    // You must write an algorithm that runs in O(n) time.

    // Example 1:
    // Input: nums = [100,4,200,1,3,2]
    // Output: 4
    // Explanation: The longest consecutive elements sequence 
    // is [1, 2, 3, 4]. Therefore its length is 4.

    // Example 2:
    // Input: nums = [0,3,7,2,5,8,4,6,0,1]
    // Output: 9

    // Example 3:
    // Input: nums = [1,0,1,2]
    // Output: 3
    
    // Constraints:
    // - 0 <= nums.length <= 105
    // - -109 <= nums[i] <= 109
    public int longestConsecutive(int[] nums) {
        // if (nums.length == 0) {
        //     return 0;
        // }
        // Arrays.sort(nums);
        // int i = 1;
        // int maxSubSeq = Integer.MIN_VALUE;
        // while (i < nums.length) {
        //     int count = 1;
        //     if (nums[i] - nums[i - 1] <= 1) {
        //         while (i < nums.length && nums[i] - nums[i - 1] <= 1) {
        //             if (nums[i] - nums[i - 1] == 1) {
        //                 count++;
        //                 maxSubSeq = Math.max(maxSubSeq, count);
        //             }
        //             i++;
        //         }
        //     } else {
        //         i++;
        //     }
        // }

        // return maxSubSeq == Integer.MIN_VALUE ? 1 : maxSubSeq;

        if (nums.length == 0) {
            return 0;
        }
        Set<Integer> data = new HashSet<>();
        for (int n : nums) {
            data.add(n);
        }

        int maxSubSeq = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int prev = nums[i] - 1;
            int next = nums[i] + 1;
            if (data.contains(prev) && data.contains(next)) {
                continue;
            }
            int count = 1;
            while (data.contains(next)) {
                data.remove(next);
                next++;
                count++;
                maxSubSeq = maxSubSeq < count ? count : maxSubSeq;
            }
        }

        return maxSubSeq == Integer.MIN_VALUE ? 1 : maxSubSeq;
    }

    // Given an integer array nums, return all the triplets 
    // [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, 
    // and nums[i] + nums[j] + nums[k] == 0.

    // Notice that the solution set must not contain duplicate triplets.

    // Example 1:
    // Input: nums = [-1,0,1,2,-1,-4]
    // Output: [[-1,-1,2],[-1,0,1]]
    // Explanation: 
    // nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
    // nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
    // nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
    // The distinct triplets are [-1,0,1] and [-1,-1,2].
    // Notice that the order of the output and the order of the triplets does not matter.

    // Example 2:
    // Input: nums = [0,1,1]
    // Output: []
    // Explanation: The only possible triplet does not sum up to 0.

    // Example 3:
    // Input: nums = [0,0,0]
    // Output: [[0,0,0]]
    // Explanation: The only possible triplet sums up to 0.
    
    // Constraints:

    // - 3 <= nums.length <= 3000
    // - -105 <= nums[i] <= 105
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int l = nums[left], r = nums[right], n = nums[i];
                int sum = l + r + n;
                if (sum == 0) {
                    result.add(Arrays.asList(l, r, n));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }

    // You are given an integer array height of length n. 
    // There are n vertical lines drawn such that the two 
    // endpoints of the ith line are (i, 0) and (i, height[i]).

    // Find two lines that together with the x-axis form a container, 
    // such that the container contains the most water.

    // Return the maximum amount of water a container can store.
    // Notice that you may not slant the container.

    // Example 1:
    // Input: height = [1,8,6,2,5,4,8,3,7]
    // Output: 49
    // Explanation: The above vertical lines are represented 
    // by array [1,8,6,2,5,4,8,3,7]. In this case, the max area 
    // of water (blue section) the container can contain is 49.

    // Example 2:
    // Input: height = [1,1]
    // Output: 1
    
    // Constraints:
    // - n == height.length
    // - 2 <= n <= 105
    // - 0 <= height[i] <= 104
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = Integer.MIN_VALUE;
        while (left < right) {
            int hl = height[left];
            int rl = height[right];
            int area = Math.min(hl, rl) * (right - left);
            maxArea = Math.max(maxArea, area);
            if (hl == rl) {
                left++;
                right--;
            } else if (hl < rl) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    // Given n non-negative integers representing an elevation map where 
    // the width of each bar is 1, compute how much water it can trap after raining.

    // Example 1:
    // Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
    // Output: 6
    // Explanation: The above elevation map (black section) is 
    // represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 
    // 6 units of rain water (blue section) are being trapped.

    // Example 2:
    // Input: height = [4,2,0,3,2,5]
    // Output: 9
    
    // Constraints:
    // - n == height.length
    // - 1 <= n <= 2 * 104
    // - 0 <= height[i] <= 105
    public int trap(int[] height) {
        int[] leftMax = new int[height.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            max = Math.max(max, height[i]);
            leftMax[i] = max;
        }

        max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            max = Math.max(max, height[i]);
            sum += Math.abs(Math.min(leftMax[i], max) - height[i]);
        }

        return sum;
    }

    // You are given an array prices where prices[i] is 
    // the price of a given stock on the ith day.
    // You want to maximize your profit by choosing a 
    // single day to buy one stock and choosing a 
    // different day in the future to sell that stock.
    // Return the maximum profit you can achieve from 
    // this transaction. If you cannot achieve any profit, return 0.

    // Example 1:
    // Input: prices = [7,1,5,3,6,4]
    // Output: 5
    // Explanation: Buy on day 2 (price = 1) and sell on 
    // day 5 (price = 6), profit = 6-1 = 5.
    // Note that buying on day 2 and selling on day 1 
    // is not allowed because you must buy before you sell.

    // Example 2:
    // Input: prices = [7,6,4,3,1]
    // Output: 0
    // Explanation: In this case, no transactions are done and the max profit = 0.
    
    // Constraints:

    // - 1 <= prices.length <= 105
    // - 0 <= prices[i] <= 104
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int buy = 0, sell = 1;
        int maxProfit = Integer.MIN_VALUE;
        while (buy < prices.length && sell < prices.length) {
            int profit = prices[sell] - prices[buy];
            if (profit < 0) {
                buy = sell;
                sell = buy + 1;
            } else {
                maxProfit = Math.max(profit, maxProfit);
                sell++;
            }
        }

        return maxProfit != Integer.MIN_VALUE ? maxProfit : 0;
    }

    // Given a string s, find the length of the longest 
    // substring without duplicate characters.

    // Example 1:
    // Input: s = "abcabcbb"
    // Output: 3
    // Explanation: The answer is "abc", with the length of 3. 
    // Note that "bca" and "cab" are also correct answers.

    // Example 2:
    // Input: s = "bbbbb"
    // Output: 1
    // Explanation: The answer is "b", with the length of 1.

    // Example 3:
    // Input: s = "pwwkew"
    // Output: 3
    // Explanation: The answer is "wke", with the length of 3.
    // Notice that the answer must be a substring, 
    // "pwke" is a subsequence and not a substring.
    
    // Constraints:
    // - 0 <= s.length <= 5 * 104
    // - s consists of English letters, digits, symbols and spaces.
    public int lengthOfLongestSubstring(String s) {
        if (s.length() <= 1) {
            return s.length();
        }

        int left = 0, right = 0, maxLen = Integer.MIN_VALUE;
        Set<Character> data = new HashSet<>();
        while (right < s.length()) {
            char c = s.charAt(right);
            while (data.contains(c)) {
                data.remove(s.charAt(left));
                left++;
            }
            data.add(c);
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }

        return maxLen;
    }

    // You are given a string s and an integer k. You can 
    // choose any character of the string and change it 
    // to any other uppercase English character. 
    // You can perform this operation at most k times.

    // Return the length of the longest substring containing 
    // the same letter you can get after performing the above operations.

    // Example 1:
    // Input: s = "ABAB", k = 2
    // Output: 4
    // Explanation: Replace the two 'A's with two 'B's or vice versa.

    // Example 2:
    // Input: s = "AABABBA", k = 1
    // Output: 4
    // Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
    // The substring "BBBB" has the longest repeating letters, which is 4.
    // There may exists other ways to achieve this answer too.
    
    // Constraints:
    // - 1 <= s.length <= 105
    // - s consists of only uppercase English letters.
    // - 0 <= k <= s.length
    public int characterReplacement(String s, int k) {
        int[] occurence = new int[26];
        int left = 0;
        int result = 0;
        int maxOccurence = 0;
        for (int right = 0; right < s.length(); right++) {
            maxOccurence = Math.max(maxOccurence, 
                ++occurence[s.charAt(right) - 'A']);
            int windowSize = right - left + 1;
            if (windowSize - maxOccurence > k) {
                occurence[s.charAt(left) - 'A']--;                
                left++;
            }
            result = Math.max(result, right - left + 1);
        }

        return result;
    }

    // Given two strings s1 and s2, return true if 
    // s2 contains a permutation of s1, or false otherwise.
    // In other words, return true if one of s1s 
    // permutations is the substring of s2.

    // Example 1:
    // Input: s1 = "ab", s2 = "eidbaooo"
    // Output: true
    // Explanation: s2 contains one permutation of s1 ("ba").

    // Example 2:
    // Input: s1 = "ab", s2 = "eidboaoo"
    // Output: false
    
    // Constraints:
    // - 1 <= s1.length, s2.length <= 104
    // - s1 and s2 consist of lowercase English letters.
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] register = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            register[s1.charAt(i) - 'a']++;
        }

        int left = 0, right = s1.length() - 1;
        int[] checker = new int[26];
        for (int i = left; i <= right; i++) {
            checker[s2.charAt(i) - 'a']++;
        }

        while (right < s2.length()) {
            if (Arrays.equals(register, checker)) {
                return true;
            }
            checker[s2.charAt(right - s1.length() + 1) - 'a']--;
            right++;
            if (right < s2.length()) {
                checker[s2.charAt(right) - 'a']++;
            }
        }

        return false;
    }

    // Given two strings s and t of lengths m and n respectively, 
    // return the minimum window substring of s such that every 
    // character in t (including duplicates) is included in the window. 
    // If there is no such substring, return the empty string "".

    // The testcases will be generated such that the answer is unique.

    // Example 1:
    // Input: s = "ADOBECODEBANC", t = "ABC"
    // Output: "BANC"
    // Explanation: The minimum window substring "BANC" 
    // includes 'A', 'B', and 'C' from string t.

    // Example 2:
    // Input: s = "a", t = "a"
    // Output: "a"
    // Explanation: The entire string s is the minimum window.

    // Example 3:
    // Input: s = "a", t = "aa"
    // Output: ""
    // Explanation: Both 'a's from t must be included in the window.
    // Since the largest window of s only has one 'a', return empty string.
    
    // Constraints:
    // - m == s.length
    // - n == t.length
    // - 1 <= m, n <= 105
    // - s and t consist of uppercase and lowercase English letters.
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        
        // Frequency array for target string t (128 for all ASCII chars)
        int[] tFreq = new int[128];
        int required = 0; // Count of characters we need to match
        
        for (char c : t.toCharArray()) {
            if (tFreq[c] == 0) {
                required++;
            }
            tFreq[c]++;
        }
        
        int[] windowFreq = new int[128];
        int formed = 0; // Count of characters currently matched
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int leftStart = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            windowFreq[c]++;
            
            // If we just satisfied the requirement for this character
            if (tFreq[c] > 0 && windowFreq[c] == tFreq[c]) {
                formed++;
            }

            // Try to shrink window while valid
            while (formed == required) {
                // Update result if current window is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    leftStart = left;
                }

                // Shrink from left
                char leftChar = s.charAt(left);
                windowFreq[leftChar]--;

                // If removing this character breaks the requirement
                if (tFreq[leftChar] > 0 && windowFreq[leftChar] < tFreq[leftChar]) {
                    formed--;
                }

                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(leftStart, leftStart + minLen);
    }
}
