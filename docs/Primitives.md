# Primitives
The primitives in Ash are as follows:

| Name    | Description                             | Size (bytes) | Minimum       | Maximum                        |
|---------|-----------------------------------------|--------------|---------------|--------------------------------|
| byte    | Stores a signed integer value           | 1            | -128          | 127                            |
| short   | Stores a signed integer value           | 2            | -32768        | 32767                          |
| int     | Stores a signed integer value           | 4            | -2^31         | 2^31-1                         |
| long    | Stores a signed integer value           | 8            | -2^63         | 2^63-1   
| ubyte   | Stores an unsigned integer value        | 1            | 0             | 2^8-1                         
| ushort  | Stores an unsigned integer value        | 2            | 0             | 2^16-1                         
| uint    | Stores an unsigned integer value        | 4            | 0             | 2^32-1                                               
| ulong   | Stores an unsigned integer value        | 8            | 0             | 2^64-1                         
| float   | Stores a signed floating point value    | 4            | +/- 1.4E^-45  | +/- 3.4028235E^38              |
| double  | Stores a signed floating point value    | 8            | +/- 4.9E^-324 | +/- 1.7976931348623157E^304    |
| char    | Stores a UTF-8 character                | 2            | u0000         | uFFFF                          |
| bool    | Stores a boolean value of true or false | 1            | 0 (false)     | 1 (true)                       |

Note that unsigned types have not yet been implemented.