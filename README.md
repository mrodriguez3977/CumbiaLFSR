# CumbiaLFSR
Linear Feedback Shift Register based random number generator. This was a project for my cryptography class. It passes all 15 NIST tests for random number generation, however, since this has not been deeply analyzed I would refrain from using in any serious application.

THIS SOFTWARE IS UNLICENSED, AND THUS SHOULD ONLY BE USED FOR EDUCATIONAL/NON-PROFIT PURPOSES. BY FORKING/DOWNLOADING THE CODE YOU ACKNOWLEDGE THAT I AM NOT LIABLE FOR ANY DAMAGES TO YOUR EQUIPMENT CAUSED BY THE CODE. 

If you would like a detailed explanation of which tap polynomials are used and how numbers are generated please read the "specifications" file which goes over how the registers are used and output is generated.

A sample sequence of 100 strings of 1 million bits each as well as its statistical results are attached, but feel free to run the simulator yourself and assess the results.

To run the generator simply download the Java file, change the path of the writer to what you would like to write to and adjust the parameters such as number of strings to generate and length of each string, then run the code in your IDE of choice.
