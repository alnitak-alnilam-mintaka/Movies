1. I used MVVM, Coroutines, Flows, Gson, Room, Koin, and Mockito because they are popular and convenient.
2. I separated the data, domain, and presentation layers to maintain clear responsibility boundaries.
3. I reused the interactor and the repository because all the screens share the same logic, forming a single feature or user flow.
4. I made all fields in data classes optional to avoid crashes, adding checks for null values or setting default values.
5. I used an offline-first approach to minimize server load. At work, I would discuss this with management or the backend team to determine how frequently the data is updated.
6. I used separate data models across different architecture layers because data should be tailored for the specific needs of each layer.
7. I used flows in the database to make the application reactive and easily update the movie list on the screen when it changes.
8. I used findViewById instead of DataBinding because it is more reliable and a straightforward way to organize views.

With more time, I would:

1. Build a multi-module project for better maintainability,
2. Add better and more scalable error handling using custom exceptions with localized messages,
3. Create reusable typography styles and sizes,
4. Add base classes to encapsulate common functionality,
5. Fix the rating bar (and ask for help downloading star icons from Figma!),
6. Increase test coverage and add UI tests.