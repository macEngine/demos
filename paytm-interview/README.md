### Some Notes：
1. It's a maven project, you can directly run "mvn test" in command line.

2. You can also import the project into IDEA, and run tests as you wish.

3. BigDecimal is used to deal with money amount instead of double.
   MAX_INCOME is used for binary search upper bound, which can be configured according to requirement.
   When compare BigDecimal, scale is used. 
   For computing, default scale is 10.
   For compare result, default scale is 2 (the result is a whole number, 2 is enough).
   The scales can be adjusted accordingly.

4. If you want to reach out to me about any question, 
   or have any problem to run the tests, please feel free to email dev.sunflower@gmail.com


Thanks In Advance.
Wenjie Su
