# Hadoop_Anagram
[![image](https://github.com/SotirisMeletiou/Hadoop_Anagram/assets/153329432/fba5f884-6423-4c58-814b-8145429918c7)
](https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.analyticsvidhya.com%2Fblog%2F2022%2F10%2Fhadoop-ecosystem%2F&psig=AOvVaw1mnfuqI2O7IryFWZzA0QDu&ust=1712080148617000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCQm9rJoYUDFQAAAAAdAAAAABAE)

The implementation consists of a program that utilizes the Hadoop Map-Reduce framework to identify the anagrams of the words of a file.

## But what is an anagram?
An anagram is a word or phrase formed by rearranging the letters of a different word, by using all the original characters/letters exactly once.

For example:
- Refills→fillers
- Relayed→layered
- Rentals→antlers
- Rebuild→builder

## Implementation
Examples of desired output:
- 2 hasn't,shan't
- 2 cascara,caracas
- 2 ramada,armada
- 2 drawback,backward
- 2 bacterial,calibrate
- 2 bandpass,passband
- 2 aboard,abroad
- 2 wabash,bashaw
- 3 banal,laban,nabla

The main idea of this problem's solution is to use the same Key for every word that can be rearranged together. Thus, the ideal Key for each read word to use during the mapping phase, is a Text object with the **sorted letters-characters (alphabetically)** of the read word.
