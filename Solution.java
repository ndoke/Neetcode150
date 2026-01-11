class Solution {
    /**
     * Given an array of strings strs, group the anagrams together.
     * You can return the answer in any order.
     * 
     * Example 1:
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 
     * Explanation:
     * There is no string in strs that can be rearranged to form "bat".
     * The strings "nat" and "tan" are anagrams as they can be
     * rearranged to form each other.
     * The strings "ate", "eat", and "tea" are anagrams as they
     * can be rearranged to form each other.
     * 
     * Example 2:
     * Input: strs = [""]
     * Output: [[""]]
     * 
     * Example 3:
     * Input: strs = ["a"]
     * Output: [["a"]]
     * 
     * Constraints:
     * - 1 <= strs.length <= 104
     * - 0 <= strs[i].length <= 100
     * - strs[i] consists of lowercase English letters.
     */
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

    /**
     * Given an integer array nums and an integer k,
     * return the k most frequent elements. You may return the answer in any order.
     * 
     * Example 1:
     * Input: nums = [1,1,1,2,2,3], k = 2
     * Output: [1,2]
     * 
     * Example 2:
     * Input: nums = [1], k = 1
     * Output: [1]
     * 
     * Example 3:
     * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
     * Output: [1,2]
     * 
     * Constraints:
     * - 1 <= nums.length <= 105
     * - -104 <= nums[i] <= 104
     * - k is in the range [1, the number of unique elements in the array].
     * - It is guaranteed that the answer is unique.
     * Follow up: Your algorithm's time complexity must be better than O(n log n),
     * where n is the array's size.
     */
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

    public int[] topKFrequentBucketSort(int[] nums, int k) {
        // Step 1: Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // Step 2: Create buckets - bucket[i] contains elements with frequency i
        // Maximum frequency is nums.length
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        
        for (int num : freqMap.keySet()) {
            int freq = freqMap.get(num);
            bucket[freq].add(num);
        }
        
        // Step 3: Collect k most frequent elements from highest frequency
        int[] result = new int[k];
        int index = 0;
        
        for (int i = bucket.length - 1; i >= 0 && index < k; i--) {
            for (int num : bucket[i]) {
                result[index++] = num;
                if (index == k) {
                    return result;
                }
            }
        }
        
        return result;
    }

    /**
     * Given an integer array nums, return an array answer such that
     * answer[i] is equal to the product of all the elements of nums except nums[i].
     * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
     * You must write an algorithm that runs in O(n) time and without using the division operation.
     * 
     * Example 1:
     * Input: nums = [1,2,3,4]
     * Output: [24,12,8,6]
     * 
     * Example 2:
     * Input: nums = [-1,1,0,-3,3]
     * Output: [0,0,9,0,0]
     * 
     * Constraints:
     * - 2 <= nums.length <= 105
     * - -30 <= nums[i] <= 30
     * - The input is generated such that answer[i] is guaranteed to fit in a 32-bit integer.
     * Follow up: Can you solve the problem in O(1) extra space complexity?
     * (The output array does not count as extra space for space complexity analysis.)
     */
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

    /**
     * Determine if a 9 x 9 Sudoku board is valid. Only the filled
     * cells need to be validated according to the following rules:
     * 
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain
     * the digits 1-9 without repetition.
     * 
     * Note:
     * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
     * Only the filled cells need to be validated according to the mentioned rules.
     * 
     * Example 1:
     * Input: board =
     * [["5","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: true
     * 
     * Example 2:
     * Input: board =
     * [["8","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: false
     * Explanation: Same as Example 1, except with the 5 in the top left
     * corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
     * 
     * Constraints:
     * - board.length == 9
     * - board[i].length == 9
     * - board[i][j] is a digit 1-9 or '.'.
     */
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

    /**
     * Given an unsorted array of integers nums,
     * return the length of the longest consecutive elements sequence.
     * 
     * You must write an algorithm that runs in O(n) time.
     * 
     * Example 1:
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
     * Therefore its length is 4.
     * 
     * Example 2:
     * Input: nums = [0,3,7,2,5,8,4,6,0,1]
     * Output: 9
     * 
     * Example 3:
     * Input: nums = [1,0,1,2]
     * Output: 3
     * 
     * Constraints:
     * - 0 <= nums.length <= 105
     * - -109 <= nums[i] <= 109
     */
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        // Build HashSet for O(1) lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int maxLength = 0;
        
        // Iterate through the set (not the array) to avoid duplicates
        for (int num : numSet) {
            // Only start counting if this is the beginning of a sequence
            // Key optimization: skip if num-1 exists (not the start)
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                
                // Count forward from the start
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                
                // Update max after counting the full sequence
                maxLength = Math.max(maxLength, currentLength);
            }
        }
        
        return maxLength;
    }

    /**
     * Given an integer array nums, return all the triplets
     * [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k,
     * and nums[i] + nums[j] + nums[k] == 0.
     * 
     * Notice that the solution set must not contain duplicate triplets.
     * 
     * Example 1:
     * Input: nums = [-1,0,1,2,-1,-4]
     * Output: [[-1,-1,2],[-1,0,1]]
     * Explanation:
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
     * The distinct triplets are [-1,0,1] and [-1,-1,2].
     * Notice that the order of the output and the order of the triplets does not matter.
     * 
     * Example 2:
     * Input: nums = [0,1,1]
     * Output: []
     * Explanation: The only possible triplet does not sum up to 0.
     * 
     * Example 3:
     * Input: nums = [0,0,0]
     * Output: [[0,0,0]]
     * Explanation: The only possible triplet sums up to 0.
     * 
     * Constraints:
     * - 3 <= nums.length <= 3000
     * - -105 <= nums[i] <= 105
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Early termination: if smallest number is positive, no solution
            if (nums[i] > 0) {
                break;
            }
            
            // Optimization: if smallest possible sum is too large, break
            if (nums[i] + nums[i + 1] + nums[i + 2] > 0) {
                break;
            }
            
            // Optimization: if largest possible sum is too small, skip this i
            if (nums[i] + nums[n - 2] + nums[n - 1] < 0) {
                continue;
            }
            
            int left = i + 1;
            int right = n - 1;
            int target = -nums[i]; // We want left + right = target
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for right pointer
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }

    /**
     * You are given an integer array height of length n.
     * There are n vertical lines drawn such that the two
     * endpoints of the ith line are (i, 0) and (i, height[i]).
     * 
     * Find two lines that together with the x-axis form a container,
     * such that the container contains the most water.
     * 
     * Return the maximum amount of water a container can store.
     * Notice that you may not slant the container.
     * 
     * Example 1:
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     * Explanation: The above vertical lines are represented
     * by array [1,8,6,2,5,4,8,3,7]. In this case, the max area
     * of water (blue section) the container can contain is 49.
     * 
     * Example 2:
     * Input: height = [1,1]
     * Output: 1
     * 
     * Constraints:
     * - n == height.length
     * - 2 <= n <= 105
     * - 0 <= height[i] <= 104
     */
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

    /**
     * Given n non-negative integers representing an elevation map
     * where the width of each bar is 1, compute how much water it can trap after raining.
     * 
     * Example 1:
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     * Explanation: The above elevation map (black section) is
     * represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case,
     * 6 units of rain water (blue section) are being trapped.
     * 
     * Example 2:
     * Input: height = [4,2,0,3,2,5]
     * Output: 9
     * 
     * Constraints:
     * - n == height.length
     * - 1 <= n <= 2 * 104
     * - 0 <= height[i] <= 105
     */
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
    /**
     * You are given an array prices where prices[i] is
     * the price of a given stock on the ith day.
     * You want to maximize your profit by choosing
     * a single day to buy one stock and choosing a
     * different day in the future to sell that stock.
     * Return the maximum profit you can achieve from
     * this transaction. If you cannot achieve any profit, return 0.
     * 
     * Example 1:
     * Input: prices = [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on
     * day 5 (price = 6), profit = 6-1 = 5.
     * Note that buying on day 2 and selling on day 1
     * is not allowed because you must buy before you sell.
     * 
     * Example 2:
     * Input: prices = [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transactions are done and the max profit = 0.
     * 
     * Constraints:
     * - 1 <= prices.length <= 105
     * - 0 <= prices[i] <= 104
     */
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

    /**
     * Given a string s, find the length of the longest
     * substring without repeating characters.
     * 
     * Example 1:
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Note that "bca" and "cab" are also correct answers.
     * 
     * Example 2:
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * 
     * Example 3:
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring,
     * "pwke" is a subsequence and not a substring.
     * 
     * Constraints:
     * - 0 <= s.length <= 5 * 104
     * - s consists of English letters, digits, symbols and spaces.
     */
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

    /**
     * You are given a string s and an integer k. You can
     * choose any character of the string and change it
     * to any other uppercase English character.
     * You can perform this operation at most k times.
     * 
     * Return the length of the longest substring containing
     * the same letter you can get after performing the above operations.
     * 
     * Example 1:
     * Input: s = "ABAB", k = 2
     * Output: 4
     * Explanation: Replace the two 'A's with two 'B's or vice versa.
     * 
     * Example 2:
     * Input: s = "AABABBA", k = 1
     * Output: 4
     * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
     * The substring "BBBB" has the longest repeating letters, which is 4.
     * There may exists other ways to achieve this answer too.
     * 
     * Constraints:
     * - 1 <= s.length <= 105
     * - s consists of only uppercase English letters.
     * - 0 <= k <= s.length
     */
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

    /**
     * Given two strings s1 and s2, write a function to return true
     * if s2 contains the permutation of s1. In other words,
     * one of the first string's permutations is the substring of the second string.
     * 
     * Example 1:
     * Input: s1 = "ab", s2 = "eidbaooo"
     * Output: true
     * Explanation: s2 contains one permutation of s1 ("ba").
     * 
     * Example 2:
     * Input: s1 = "ab", s2 = "eidboaoo"
     * Output: false
     * 
     * Constraints:
     * - 1 <= s1.length, s2.length <= 104
     * - s1 and s2 consist of lowercase English letters.
     */
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

    /**
     * Given two strings s and t of lengths m and n respectively,
     * return the minimum window substring of s such that every
     * character in t (including duplicates) is included in the window.
     * If there is no such substring, return the empty string "".
     * 
     * The testcases will be generated such that the answer is unique.
     * 
     * Example 1:
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     * Explanation: The minimum window substring "BANC"
     * includes 'A', 'B', and 'C' from string t.
     * 
     * Example 2:
     * Input: s = "a", t = "a"
     * Output: "a"
     * Explanation: The entire string s is the minimum window.
     * 
     * Example 3:
     * Input: s = "a", t = "aa"
     * Output: ""
     * Explanation: Both 'a's from t must be included in the window.
     * Since the largest window of s only has one 'a', return empty string.
     * 
     * Constraints:
     * - m == s.length
     * - n == t.length
     * - 1 <= m, n <= 105
     * - s and t consist of uppercase and lowercase English letters.
     */
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

    /**
     * You are given an array of integers nums and an integer k.
     * 
     * You are given an array of integers nums, there is a sliding 
     * window of size k which is moving from the very left of the 
     * array to the very right. You can only see the k numbers in 
     * the window. Each time the sliding window moves right by one position.
     * 
     * Return the max sliding window.
     * 
     * Example 1:
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * Window position                Max
     * ---------------               -----
     * [1  3 -1] -3   5  3  6  7       3
     * 1  [3  -1 -3]  5  3  6  7       3
     * 1  3 [-1  -3   5] 3  6  7       5
     * 1  3  -1 [-3   5  3] 6  7       5
     * 1  3  -1  -3  [5  3  6] 7       6
     * 1  3  -1  -3   5 [3  6  7]      7
     * 
     * Example 2:
     * Input: nums = [1], k = 1
     * Output: [1]
     * 
     * Constraints:
     * - 1 <= nums.length <= 105
     * - -104 <= nums[i] <= 104
     * - 1 <= k <= nums.length
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (k == 1) {
            return nums;
        }

        int[] left = new int[n];   // Max from left in each block
        int[] right = new int[n];  // Max from right in each block
        
        // Build left array
        left[0] = nums[0];
        for (int i = 1; i < n; i++) {
            left[i] = (i % k == 0) ? nums[i] : Math.max(left[i - 1], nums[i]);
        }
        
        // Build right array
        right[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            right[i] = ((i + 1) % k == 0) ? nums[i] : Math.max(right[i + 1], nums[i]);
        }
        
        // Generate result
        int[] result = new int[n - k + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.max(right[i], left[i + k - 1]);
        }
        
        return result;
    }

    /**
     * You are given an array of strings tokens that represents
     * an arithmetic expression in a Reverse Polish Notation.
     * 
     * Evaluate the value of an arithmetic expression in
     * Reverse Polish Notation.
     * 
     * Example 1:
     * Input: tokens = ["2","1","+","3","*"]
     * Output: 9
     * Explanation: ((2 + 1) * 3) = 9
     * 
     * Example 2:
     * Input: tokens = ["4","13","5","/","+"]
     * Output: 6
     * Explanation: (4 + (13 / 5)) = 6
     * 
     * Example 3:
     * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
     * Output: 22
     * Explanation:
     *  ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
     * = ((10 * (6 / (12 * -11))) + 17) + 5
     * = ((10 * (6 / -132)) + 17) + 5
     * = ((10 * 0) + 17) + 5
     * = (0 + 17) + 5
     * = 17 + 5
     * = 22
     * 
     * Constraints:
     * - 1 <= tokens.length <= 104
     * - tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
     */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor);
                    break;
                default:
                    stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }

    /**
     * Given n pairs of parentheses, write a function to
     * generate all combinations of well-formed parentheses.
     * 
     * Example 1:
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     * 
     * Example 2:
     * Input: n = 1
     * Output: ["()"]
     * 
     * Constraints:
     * - 1 <= n <= 8
     */
    public List<String> generateParenthesis(int n) {
        char[] result = new char[n * 2];
        int leftRem = n, rightRem = n;
        List<String> parens = new ArrayList<>();
        generateParenthesisHelper(leftRem, rightRem, 0, result, parens);
        return parens;
    }

    private void generateParenthesisHelper(int leftRem, 
                                           int rightRem, 
                                           int index,
                                           char[] result, 
                                           List<String> parens) {
        if (leftRem < 0 || rightRem < leftRem) {
            return;
        }
        if (leftRem == 0 && rightRem == 0) {
            parens.add(new String(result));
        }
        if (leftRem > 0) {
            result[index] = '(';
            generateParenthesisHelper(leftRem - 1, rightRem, index + 1, result, parens);
        }
        if (rightRem > 0) {
            result[index] = ')';
            generateParenthesisHelper(leftRem, rightRem - 1, index + 1, result, parens);
        }
    }

    /**
     * Given an array of integers temperatures represents the
     * daily temperatures, return an array answer such that
     * answer[i] is the number of days you have to wait after
     * the ith day to get a warmer temperature. If there is
     * no future day for which this is possible, keep answer[i] == 0 instead
     * 
     * Example 1:
     * Input: temperatures = [73,74,75,71,69,72,76,73]
     * Output: [1,1,4,2,1,1,0,0]
     * 
     * Example 2:
     * Input: temperatures = [30,40,50,60]
     * Output: [1,1,1,0]
     * 
     * Example 3:
     * Input: temperatures = [30,60,90]
     * Output: [1,1,0]
     * 
     * Constraints:
     * - 1 <= temperatures.length <= 105
     * - 30 <= temperatures[i] <= 100
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] answer = new int[n];
        int[] stack = new int[n]; // index stack
        int top = -1; // stack pointer
        
        for (int i = 0; i < n; i++) {
            while (top >= 0 && temperatures[i] > temperatures[stack[top]]) {
                int prevIndex = stack[top--];
                answer[prevIndex] = i - prevIndex;
            }
            stack[++top] = i;
        }
        
        return answer;
    }

    /**
     * There are n cars at given miles away from the starting
     * mile 0, traveling to reach the mile target.
     * You are given two integer arrays position and speed,
     * both of length n, where position[i] is the starting
     * mile of the ith car and speed[i] is the speed of the
     * ith car in miles per hour.
     * 
     * A car cannot pass another car, but it can catch up and
     * then travel next to it at the speed of the slower car.
     * 
     * A car fleet is a single car or a group of cars driving
     * next to each other. The speed of the car fleet is the
     * minimum speed of any car in the fleet.
     * 
     * If a car catches up to a car fleet at the mile target,
     * it will still be considered as part of the car fleet.
     * 
     * Return the number of car fleets that will arrive at the destination.
     * 
     * Example 1:
     * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
     * Output: 3
     * Explanation:
     * The cars starting at 10 (speed 2) and 8 (speed 4) become a
     * fleet, meeting each other at 12. The fleet forms at target.
     * The car starting at 0 (speed 1) does not catch up to any
     * other car, so it is a fleet by itself.
     * The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet,
     * meeting each other at 6. The fleet moves at speed 1 until it reaches target.
     * 
     * Example 2:
     * Input: target = 10, position = [3], speed = [3]
     * Output: 1
     * Explanation:
     * There is only one car, hence there is only one fleet.
     * 
     * Example 3:
     * Input: target = 100, position = [0,2,4], speed = [4,2,1]
     * Output: 1
     * Explanation:
     * The cars starting at 0 (speed 4) and 2 (speed 2) become a fleet,
     * meeting each other at 4. The car starting at 4 (speed 1) travels to 5.
     * Then, the fleet at 4 (speed 2) and the car at position 5 (speed 1)
     * become one fleet, meeting each other at 6. The fleet moves at
     * speed 1 until it reaches target.
     * 
     * Constraints:
     * - n == position.length == speed.length
     * - 1 <= n <= 105
     * - 0 < target <= 106
     * - 0 <= position[i] < target
     * - All the values of position are unique.
     * - 0 < speed[i] <= 106
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 1) return 1;
        
        // Create array of cars with [position, time to target]
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double)(target - position[i]) / speed[i];
        }
        
        // Sort by position in descending order (closest to target first)
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
        
        int fleets = 0;
        double maxTime = 0;
        
        // Process cars from closest to target
        for (int i = 0; i < n; i++) {
            double timeToTarget = cars[i][1];
            
            // If this car takes longer than previous, it's a new fleet
            if (timeToTarget > maxTime) {
                fleets++;
                maxTime = timeToTarget;
            }
            // Otherwise, it catches up to the fleet ahead
        }
        
        return fleets;
    }

    /**
     * Given an array of integers heights representing the
     * histograms bar height where the width of each bar is 1,
     * return the area of the largest rectangle in the histogram.
     * 
     * Example 1:
     * Input: heights = [2,1,5,6,2,3]
     * Output: 10
     * Explanation: The above is a histogram where width of each bar is 1.
     * The largest rectangle is shown in the red area, which has an area = 10 units.
     * 
     * Example 2:
     * Input: heights = [2,4]
     * Output: 4
     * 
     * Constraints:
     * - 1 <= heights.length <= 105
     * - 0 <= heights[i] <= 104
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n + 1];
        int top = -1;
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            while (top >= 0 && heights[i] < heights[stack[top]]) {
                int height = heights[stack[top--]];
                int width = top < 0 ? i : i - stack[top] - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack[++top] = i;
        }
        
        while (top >= 0) {
            int height = heights[stack[top--]];
            int width = top < 0 ? n : n - stack[top] - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        return maxArea;
    }

    /**
     * Koko loves to eat bananas. There are n piles of bananas,
     * the ith pile has piles[i] bananas. The guards have gone
     * and will come back in h hours.
     * 
     * Koko can decide her bananas-per-hour eating speed of k.
     * Each hour, she chooses some pile of bananas and eats k
     * bananas from that pile. If the pile has less than k bananas,
     * she eats all of them instead and will not eat any more
     * bananas during this hour.
     * 
     * Koko likes to eat slowly but still wants to finish eating
     * all the bananas before the guards return.
     * 
     * Return the minimum integer k such that she can eat all
     * the bananas within h hours.
     * 
     * Example 1:
     * Input: piles = [3,6,7,11], h = 8
     * Output: 4
     * 
     *  Example 2:
     * Input: piles = [30,11,23,4,20], h = 5
     * Output: 30
     * 
     * Example 3:
     * Input: piles = [30,11,23,4,20], h = 6
     * Output: 23
     * 
     * Constraints:
     * - 1 <= piles.length <= 104
     * - piles.length <= h <= 109
     * - 1 <= piles[i] <= 109
     */
    public int minEatingSpeed(int[] piles, int h) {
        // Binary search bounds
        int left = 1;
        int right = getMax(piles);
        
        while (left < right) {
            int mid = (right + left) / 2;
            
            // Check if we can finish with speed mid
            if (canFinish(piles, h, mid)) {
                // Try smaller speed
                right = mid;
            } else {
                // Need faster speed
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    // Check if Koko can finish all bananas at speed k within h hours
    private boolean canFinish(int[] piles, int h, int k) {
        long hoursNeeded = 0;
        
        for (int pile : piles) {
            // Ceiling division: (pile + k - 1) / k
            hoursNeeded += (pile + k - 1) / k;
            
            // Early termination if already exceeded
            if (hoursNeeded > h) {
                return false;
            }
        }
        
        return hoursNeeded <= h;
    }
    
    private int getMax(int[] piles) {
        int max = piles[0];
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        return max;
    }

    /**
     * A permutation of an array of integers is an arrangement of
     * its members into a sequence or linear order.
     * 
     * For example, for arr = [1,2,3], the following are all the
     * permutations of arr: [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1],
     * [3,1,2], [3,2,1].
     * 
     * The next permutation of an array of integers is the next
     * lexicographically greater permutation of its integer.
     * More formally, if all the permutations of the array are
     * sorted in one container according to their lexicographical
     * order, then the next permutation of that array is the
     * permutation that follows it in the sorted container.
     * If such arrangement is not possible, the array must be
     * rearranged as the lowest possible order (i.e., sorted in ascending order).
     * 
     * For example, the next permutation of arr = [1,2,3] is [1,3,2].
     * Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
     * While the next permutation of arr = [3,2,1] is [1,2,3] because
     * [3,2,1] does not have a lexicographical larger rearrangement.
     * Given an array of integers nums, find the next permutation of nums.
     * 
     * The replacement must be in place and use only constant extra memory.
     * 
     * Example 1:
     * Input: nums = [1,2,3]
     * Output: [1,3,2]
     * 
     * Example 2:
     * Input: nums = [3,2,1]
     * Output: [1,2,3]
     * 
     * Example 3:
     * Input: nums = [1,1,5]
     * Output: [1,5,1]
     * 
     * Constraints:
     * - 1 <= nums.length <= 100
     * - 0 <= nums[i] <= 100
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        
        // Step 1: Find the pivot (rightmost i where nums[i] < nums[i+1])
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // Step 2: If pivot found, find successor and swap
        if (i >= 0) {
            // Find the smallest element greater than nums[i] from the left
            int j = n - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            // Swap pivot with its successor
            swap(nums, i, j);
        }
        
        // Step 3: Reverse the suffix after position i
        reverse(nums, i + 1, n - 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    /**
     * Suppose an array of length n sorted in ascending order
     * is rotated between 1 and n times. For example, the
     * array nums = [0,1,2,4,5,6,7] might become:
     * 
     * [4,5,6,7,0,1,2] if it was rotated 4 times.
     * [0,1,2,4,5,6,7] if it was rotated 7 times.
     * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1
     * time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
     * 
     * Given the sorted rotated array nums of unique elements,
     * return the minimum element of this array.
     * 
     * You must write an algorithm that runs in O(log n) time.
     * 
     * Example 1:
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
     * 
     * Example 2:
     * Input: nums = [4,5,6,7,0,1,2]
     * Output: 0
     * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
     * 
     * Example 3:
     * Input: nums = [11,13,15,17]
     * Output: 11
     * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
     * 
     * Constraints:
     * - n == nums.length
     * - 1 <= n <= 5000
     * - -5000 <= nums[i] <= 5000
     * - All the integers of nums are unique.
     * - nums is sorted and rotated between 1 and n times.
     */
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = (right + left) / 2;
            
            // If mid element is greater than the rightmost element,
            // the minimum is in the right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // Otherwise, the minimum is in the left half (including mid)
                right = mid;
            }
        }
        
        return nums[left];
    }

    /**
     * There is an integer array nums sorted in ascending order
     * (with distinct values).
     * 
     * Prior to being passed to your function, nums is possibly
     * left rotated at an unknown index k (1 <= k < nums.length)
     * such that the resulting array is [nums[k], nums[k+1], ...,
     * nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,5,6,7] might be left rotated by 3
     * indices and become [4,5,6,7,0,1,2].
     * 
     * Given the array nums after the possible rotation and an
     * integer target, return the index of target if it is in nums,
     * or -1 if it is not in nums.
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * Example 1:
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * 
     * Example 2:
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     * 
     * Example 3:
     * Input: nums = [1], target = 0
     * Output: -1
     * 
     * Constraints:
     * - 1 <= nums.length <= 5000
     * - -104 <= nums[i] <= 104
     * - All values of nums are unique.
     * - nums is an ascending array that is possibly rotated.
     * - -104 <= target <= 104
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = (right + left) / 2;
            
            // Found target
            if (nums[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    // Target is in the sorted left half
                    right = mid - 1;
                } else {
                    // Target is in the right half
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    // Target is in the sorted right half
                    left = mid + 1;
                } else {
                    // Target is in the left half
                    right = mid - 1;
                }
            }
        }
        
        // Target not found
        return -1;
    }

    /**
     * Design a time-based key-value data structure that can store
     * multiple values for the same key at different time stamps
     * and retrieve the keys value at a certain timestamp.
     * 
     * Implement the TimeMap class:
     * 
     * TimeMap() Initializes the object of the data structure.
     * void set(String key, String value, int timestamp) Stores
     * the key key with the value value at the given time timestamp.
     * String get(String key, int timestamp) Returns a value such
     * that set was called previously, with timestamp_prev <= timestamp.
     * If there are multiple such values, it returns the value
     * associated with the largest timestamp_prev. If there are
     * no values, it returns "".
     * 
     * Example 1:
     * Input
     * ["TimeMap", "set", "get", "get", "set", "get", "get"]
     * [[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
     * Output
     * [null, null, "bar", "bar", null, "bar2", "bar2"]
     * Explanation
     * TimeMap timeMap = new TimeMap();
     * timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
     * timeMap.get("foo", 1);         // return "bar"
     * timeMap.get("foo", 3);         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
     * timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
     * timeMap.get("foo", 4);         // return "bar2"
     * timeMap.get("foo", 5);         // return "bar2"
     * 
     * Constraints:
     * - 1 <= key.length, value.length <= 100
     * - key and value consist of lowercase English letters and digits.
     * - 1 <= timestamp <= 107
     * - All the timestamps timestamp of set are strictly increasing.
     * - At most 2 * 105 calls will be made to set and get.
     */
    class TimeMap {
        private static class TimeList {
            List<Integer> times = new ArrayList<>();
            List<String> values = new ArrayList<>();
        }

        private Map<String, TimeList> map = new HashMap<>();

        public void set(String key, String value, int timestamp) {
            TimeList tl = map.computeIfAbsent(key, k -> new TimeList());
            tl.times.add(timestamp);
            tl.values.add(value);
        }

        public String get(String key, int timestamp) {
            TimeList tl = map.get(key);
            if (tl == null) return "";

            List<Integer> times = tl.times;
            int idx = Collections.binarySearch(times, timestamp);

            if (idx >= 0) {
                return tl.values.get(idx);
            } else {
                int insertPoint = -idx - 1;
                if (insertPoint == 0) return "";
                return tl.values.get(insertPoint - 1);
            }
        }
    }

    /**
     * Given two sorted arrays nums1 and nums2 of size m
     * and n respectively, return the median of the two sorted arrays.
     * 
     * The overall run time complexity should be O(log (m+n)).
     * 
     * Example 1:
     * Input: nums1 = [1,3], nums2 = [2]
     * Output: 2.00000
     * Explanation: merged array = [1,2,3] and median is 2.
     * 
     * Example 2:
     * Input: nums1 = [1,2], nums2 = [3,4]
     * Output: 2.50000
     * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
     * 
     * Constraints:
     * - nums1.length == m
     * - nums2.length == n
     * - 0 <= m <= 1000
     * - 0 <= n <= 1000
     * - 1 <= m + n <= 2000
     * - -106 <= nums1[i], nums2[i] <= 106
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        if (total % 2 == 1) {
            return findKth(nums1, 0, nums2, 0, total / 2 + 1);
        } else {
            int mid1 = findKth(nums1, 0, nums2, 0, total / 2);
            int mid2 = findKth(nums1, 0, nums2, 0, total / 2 + 1);
            return (mid1 + mid2) / 2.0;
        }
    }
    
    // Find the k-th smallest element (k is 1-indexed)
    private int findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        while (true) {
            // If nums1 is exhausted, return k-th element from nums2
            if (start1 >= nums1.length) {
                return nums2[start2 + k - 1];
            }
            // If nums2 is exhausted, return k-th element from nums1
            if (start2 >= nums2.length) {
                return nums1[start1 + k - 1];
            }
            // Base case: if k == 1, return the smaller of the two current elements
            if (k == 1) {
                return Math.min(nums1[start1], nums2[start2]);
            }
            
            // Compare the k/2-th elements from both arrays
            int half = k / 2;
            int mid1 = start1 + half - 1 < nums1.length ? nums1[start1 + half - 1] : Integer.MAX_VALUE;
            int mid2 = start2 + half - 1 < nums2.length ? nums2[start2 + half - 1] : Integer.MAX_VALUE;
            
            // Eliminate the smaller half
            if (mid1 < mid2) {
                start1 += half;
                k -= half;
            } else {
                start2 += half;
                k -= half;
            }
        }
    }

    /**
     * Given an array of integers nums sorted in non-decreasing
     * order, find the starting and ending position of a given
     * target value.
     * 
     * If target is not found in the array, return [-1, -1].
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * Example 1:
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     * 
     * Example 2:
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     * 
     * Example 3:
     * Input: nums = [], target = 0
     * Output: [-1,-1]
     * 
     * Constraints:
     * - 0 <= nums.length <= 105
     * - -109 <= nums[i] <= 109
     * - nums is a non-decreasing array.
     * - -109 <= target <= 109
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        
        // Find the leftmost (starting) position
        result[0] = findBound(nums, target, true);
        
        // If target not found, no need to search for right bound
        if (result[0] == -1) {
            return result;
        }
        
        // Find the rightmost (ending) position
        result[1] = findBound(nums, target, false);
        
        return result;
    }
    
    private int findBound(int[] nums, int target, boolean isLeft) {
        int left = 0;
        int right = nums.length - 1;
        int bound = -1;
        
        while (left <= right) {
            int mid = (right + left) / 2;
            
            if (nums[mid] == target) {
                bound = mid;
                // If looking for left bound, continue searching left
                // If looking for right bound, continue searching right
                if (isLeft) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return bound;
    }

    /**
     * Given the head of a singly linked list, reverse the list, 
     * and return the reversed list.
     * 
     * Example 1:
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     * 
     * Example 2:
     * Input: head = [1,2]
     * Output: [2,1]
     * 
     * Example 3:
     * Input: head = []
     * Output: []
     * 
     * Constraints:
     * - The number of nodes in the list is the range [0, 5000].
     * - -5000 <= Node.val <= 5000
     * 
     * Follow up: A linked list can be reversed either iteratively or 
     * recursively. Could you implement both?
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next; // Store next node
            current.next = prev;               // Reverse the link
            prev = current;                    // Move prev forward
            current = nextTemp;                // Move current forward
        }
        
        return prev; // New head of the reversed list
    }

    /**
     * You are given the heads of two sorted linked lists list1 and list2.
     * 
     * Merge the two lists in a one sorted list. The list should be made
     * by splicing together the nodes of the first two lists.
     * 
     * Return the head of the merged linked list.
     * 
     * Example 1:
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     * 
     * Example 2:
     * Input: list1 = [], list2 = []
     * Output: []
     * 
     * Example 3:
     * Input: list1 = [], list2 = [0]
     * Output: [0]
     * 
     * Constraints:
     * - The number of nodes in both lists is in the range [0, 50
     * - -100 <= Node.val <= 100
     * - Both list1 and list2 are sorted in non-decreasing order.
     */
    public ListNode mergeTwoLists(ListNode headA, ListNode headB) {
        // a dummy first node to hang the result on
        ListNode dummyNode = new ListNode(0);
        // tail points to the last result node
        ListNode tail = dummyNode;
        while(true) {

            // if either list runs out, use the other list
            if(headA == null) {
                tail.next = headB;
                break;
            }
            if(headB == null) {
                tail.next = headA;
                break;
            }

            // Compare the data of the two lists whichever lists' data is
            // smaller, append it into tail and advance the head to the next Node
            if(headA.val <= headB.val) {
                tail.next = headA;
                headA = headA.next;
            } else {
                tail.next = headB;
                headB = headB.next;
            }

            // Advance the tail
            tail = tail.next;
        }
        return dummyNode.next;
    }
}
