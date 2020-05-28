# Rover Coding Project

Rover.com was destroyed in a terrible Amazon and GitHub accident.
Thankfully, no dogs were harmed, but we have to rebuild our site using data we retrieved from the Google search index.
We'd like to:

- Rebuild our sitter profiles and user accounts.
- Recreate a search ranking algorithm
- Build an appealing search results page

**Please use the languages and frameworks that you feel will best show your skills. Keep in mind that if you are brought for an in-person interview, you will continue building upon this solution. Don't use this project as an opportunity to learn new frameworks or new versions of known frameworks; use what you know best so that you set yourself up for success.**

The work you create here should be representative of code that we'd expect to receive from you if you were hired tomorrow (proper abstractions, unit tests, best practices, etc). We encourage you to to add a readme (or update the existing one) to help us understand your approach and thought process, design choices, trade-offs, dependencies, etc. Please include instructions on how to setup/run your project locally. Given the scarcity of time, please also document any implementation trade-offs that you made (ex. I wrote tests for X, but I would also write tests for Y and Z if the time would permit, I didn't spend much time on the front-end, but normally I would do X).

Finally, this is not a trick project, so if you have any questions, don't hesitate to ask.

### When you're done with the project...

When you're done with the project, push your work back into the repo.  Then, reply to the email you received from us letting us know you've pushed your project.

## Rebuilding Profiles

We were able to write a script and scrape the Google index for all of the reviews customers have left of sitters.
We have saved that information in the attached CSV.
Using the information in the file, we need to design a database schema and import the data from the .csv file.

**NOTE**: If a stay includes multiple dogs, those names will be included in the same column of the CSV "|" delimited.

## Recreating the Search Ranking Algorithm

- For each sitter, we calculate Overall Sitter Rank.
- Sitter Score is 5 times the fraction of the English alphabet comprised by the distinct letters in what we've recovered of the sitter's name.
- Ratings Score is the average of their stay ratings.
- The Overall Sitter Rank is a weighted average of the Sitter Score and Ratings Score, weighted by the number of stays. When a sitter has no stays, their Overall Sitter Rank is equal to the Sitter Score.  When a sitter has 10 or more stays, their Overall Sitter Rank is equal to the Ratings Score.
- In the event that two or more sitters have the same Overall Sitter Rank, the ordering is unimportant and does not need to be handled.

The Overall Sitter Rank and it's score components must be kept up to date. That means whenever a relevant event happens, that could affect the Overall Sitter Rank, we need to recompute it.

Think about what can make the Overall Sitter Rank change.

## Building a Sitter List

We need to display the sitters on a page in order of their *Overall Sitter Rank*. This should be easy, simply render a list of sitters.

Each row should display one sitter with their name, photo and their *Ratings Score*. We want to display just their *Ratings Score*, but sort by their *Overall Sitter Rank*. Think of the *Ratings Score* as a publicly disclosed concept and sitter attribute, and the *Overall Sitter Rank* as Rover's marketplace "secret sauce" that should remain private.

**NOTE**: Make sure your search sorting and listing can scale well to a large number of records.

## Filtering Sitters

Finally, we need to allow customers to filter out sitters on the page with poor average stay ratings.
Allow users to filter out sitters whose average ratings is below a user specified value.
It’s okay to use UI controls &mdash; sliders, checkboxes, etc &mdash; that limit the values users can enter.

## Hint for Testing the Search Ranking Algorithm
Suppose there is a sitter whose Sitter Score is 2.5 and gets rating of 5.0 with every stay. Then, their score should
behave like:

| Stay          | Overall Sitter Rank         |
| ------------- | ------------- |
| 0 | 2.50
| 1 | 2.75
| 2 | 3.00
| 3 | 3.25
| 4 | 3.50
| 5 | 3.75
| 6 | 4.00
| 7 | 4.25
| 8 | 4.50
| 9 |  4.75
| 10 | 5.00
| 11 | 5.00
| 12 | 5.00
