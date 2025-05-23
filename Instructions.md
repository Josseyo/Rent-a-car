D0017D Examination: LTU Rent-a-car
1 Assignment description
Your task is to implement a short-term car rental system called LTU Rent-a-car. The program is
supposed to handle rental cars, as well as the rental process.
Menu At the start, the program should print a menu of options the user can select. It should be
possible to exit the program by pressing q .
# LTU Rent-a-car
1. Add car to fleet
2. Rent a car
3. Return a car
4. Print car fleet
5. Print rental summary
q. End program
> Enter your option:
Add car to fleet A car is registered by providing its registration number, make and model. Reg-
istration number should start with three (3) capital letters, followed by three (3) digits (for example
PXT083, CYW426 etc.). Make and model can be stored in one string variable. Registration number
is to be used to reference the car in the program’s other menu options. Therefore, registration number
is unique (there can not be multiple cars with the same registration number).
> Enter your option: 1
> Enter registration number: CYW426
> Enter make and model: BMW 330i xDrive
BMW 330i xDrive with registration number CYW426 was added to car fleet.
Rent a car A car is rented by providing registration number of an existing car, time of renting and
renter’s name. It should not be possible to rent a car that is currently rented. Make sure to implement
appropriate error handling.
> Enter your option: 2
> Enter car's registration number: CYW426
> Enter time of pickup: 12:05
> Enter renter's name: Maxim Khamrakulov
Car with registration number CYW426 was rented by Maxim Khamrakulov at 12:05.
2
Return a car A car is returned by providing registration number of a rented car (make sure to
check that the car is rented by someone before returning it, display an error message otherwise) and
time of return. Note: in this assignment it is not possible to rent a car overnight, therefore, the return
time is the same calendar day as the pickup time (make sure to implement a check that ensures that
pickup time is before return time).
The program should calculate cost of renting (120 SEK/hour) and display it in a receipt.
> Enter your option: 3
> Enter registration number: CYW426
> Enter time of return: 18:35
===================================
LTU Rent-a-car
===================================
Name: Maxim Khamrakulov
Car: BMW 330i xDrive (CYW426)
Time: 12:05-18:35 (8.5 hours)
Total cost: 1020 SEK
NOTE: Make sure your output for the receipt follows format of the example, including strings like “ Name ” ,
“ Car ” , “ Time ” , “ Total cost ” .
Print car fleet It should be possible to print the car fleet. This functionality should present following
information:
• Table of all cars with following information:
– Car model.
– Registration number.
– Status (Rented/Available).
• Total number of cars.
• Total number of available cars.
> Enter your option: 4
LTU Rent-a-car car fleet:
Fleet:
Model Numberplate Status
BMW 330i xDrive CYW426 Rented
Skoda Enyaq iv 80x DWW341 Available
Tesla Model Y LR RUC654 Available
Audi A3 Sportback TRW499 Rented
Total number of cars: 4
Total number of available cars: 2
NOTE: Make sure your output follows format of the example, including strings like “ Total number of
cars ” , “ Total number of available cars ”, etc..
Print rental summary It should be possible to print the rental summary. The summary should
consist of following information:
• Table of all rentals sorted by renter’s name (see example). NOTE! If you are short on time, it is
better to skip the sorting part. Just print unsorted data to get some points for this task.
3
– Renter’s name.
– Car registration number.
– Time of pickup.
– Time of return (if the car has not been returned yet, just leave the value blank).
– Cost (if the car has not been returned yet, just leave the value blank)
– Number of rentals for the day.
• Generated income of all rentals (revenue).
> Enter your option: 5
LTU Rent-a-car rental summary:
Rentals:
Name Numberplate Pickup Return Cost
Erik Karlsson TRW499 07:00
Maxim Khamrakulov CYW426 12:05 18:35 1020 SEK
Sandeep Patil RUC654 16:00 20:30 540 SEK
Total number of rentals: 3
Total revenue: 1560 SEK
NOTE: Make sure your output follows format of the example, including strings like “ Total number of
rentals”, “ Total revenue ”, etc..
2 Instructions
The program should have necessary error handling (ways of validating the input, etc.). The program
should under no circumstances crash when receiving incorrect input. It is allowed to store additional
information, other than provided in the assignment description. You should create appropriate data
structures to store the information. All eventual floating point numbers should be rounded up to two
(2) decimals. The programs that compile with the compilation error will not be graded (it should be
possible to run the program).
Make sure your program’s output matches the string templates below. You can add additional information to
your printed messages, just make sure that provided strings are part of your message.
Normal output strings:
Command/menu item Message Note
Add car Make Model ... XXX000 ... added Make Model – any string matching
make and model that user entered
XXX000 – car registration number (X –
any letter, 0 – any digit)
Rent car XXX000 ... rented ... Renter Name ...
hh:mm
XXX000 – car registration number (X –
any letter, 0 – any digit)
hh:mm – time of pickup
Return car Receipt formatted according to the
example above
Print fleet See example
Pring rental summary See example
Error messages:
Command/menu item Error message Note
menu invalid menu item
4
Add car invalid registration number If user entered anything except 3 letters
followed by 3 digits
Add car number ... already exists User is trying to add a car number that is
already in the system
Rent car invalid registration number
Rent car car ... not found Requested registration number not found
Rent car car ... not available Requested car is already rented
Rent car invalid time format Invalid time format for car pick up and
drop off. This case should also handle
correctly formatted time, but outside
allowed range: for example 25:67
Return car invalid registration number
Return car car ... not found Requested registration number not found
Return car car ... not rented Requested car not rented (i.e. available)
Return car invalid time format Invalid time format for car pick up and
drop off. This case should also handle
correctly formatted time, but outside
allowed range: for example 25:67
If any error occurs, your program is always expected to print an appropriate error message and
return to the menu. It should never crash, or exit on it own, unless the user selected ”q” option in the
menu.
3 Tips
• Do one functionality/method/option at a time. It is possible to pass the assignment by not
finalizing all the parts! Leave the hardest parts till the end. For example, first get the program
working for correct/valid inputs. Then update the program to handle error conditions.
• Write down requirements for each function as comment on top of the function, this will help you
not to forget a requirement.
4 Assumptions
• If you notice that some information is missing from the assignment description, you are allowed
to make own assumptions as long as it does not change the condition and basis of the assignment.
• It is ok to assume fixed number for your data structures, for example, it is fine to assume that
there can be maximum 100 rentals for a day. This is just an example. This is so that you don’t
have to handle expanding the array to add more data.
5 Requirements
• Please do not forget to include your name, LTU username on the top of the file as
a author
• You are free to use either codepace or any IDE of your choice.
• Do not use packages (there should not be any code, beginning with package).
• Use methods in your solution.
5
• It is not allowed to use any global variables (only global Scanner objects are allowed).
• It is not allowed to use built in functionality (such as ArrayLists etc.), in other words, same
rules as your assignments.

