# AJ_Rositsa-Nikolova
Uppgift 2 , Grit Academy Malmö, Java23, Rositsa Nikolova

This code is part of learning process. 

Keep in mind that removing the files from src/Files and trying to use the buttons Read CSV, Read JSON or Read XML will cause errors. 
The code does not do verification on the entering fields because of missing specifications for expected values, formats osv.
Idially a check meeting the criteria for each read value should be implemented but that is outside the scope of the task and thus missing from the provided specifications.

In this code you can find ideas from other sources. These sources are written bellow: 
Print a table after reading a file: https://forums.oracle.com/ords/apexds/post/creating-columns-dynamically-6981
Table: createColumn() method. 
The creation of the columns was already implemented from before that but the way to fill the table is fully loaned. 

For reading XML file I used lots of sources but the most helpful and informative ones were: source https://stackoverflow.com/questions/61948901/java-get-tag-name-of-a-node and a very special credits to Professor Saad and his video Read XML Document in Java using DOM found on link-> https://youtu.be/2JH5YeQ68H8
Just like reading CSV and JSON I needed to create an Array of columns so I had to find that first row and get its columns with the assumtion that the amount and name of them will be the same. 
Then I found the name of the attribute, in that case row.
