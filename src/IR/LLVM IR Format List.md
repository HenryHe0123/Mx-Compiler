# LLVM IR Format List

```
int: i32
bool: i1
string: ptr (i8*) 
void*: ptr
```

+ ret

```
ret void
ret <type> <operand>
; ex: ret i32 0
; <type>: i32 , i8 , [num x i32] , [num x i8] , %MyStruct ...
; <operand>: 0 , %a , @global_variable ...
```

+ alloca

```
%<name> = alloca <type>
; ex: %i = alloca i32 
```

+ store

```
store <type> <src operand>, ptr <dest operand>
; ex: store i32 0, ptr %i 
```

+ load

```
%<name> = load <type>, ptr <operand>
; ex: %i_value = load i32, ptr %i
```

+ binary (add, sub, mul, div...)

```
%<name> = <op> <type> <operand1>, <operand2>
; ex: %1 = add i32 %i_value, 1
```

+ br

```
br label <label>
br i1 <cond>, label <label1>, label <label2>
; ex: br i1 %result, label %btrue, label %bfalse
```

+ icmp

```
%<name> = icmp <op> <type> <operand1>, <operand2>
; ex: %result = icmp sgt i32 %x, 0
```

+ global

```
@<name> = global <type> <value>
@<name> = constant <type> c"<str>"
; ex: @str = constant [14 x i8] c"Hello, World!\0A"
```

+ class

```
%class.<name> = type { <type1>, <type2>, ... }
; ex: %class.A = type { i32 }
```

+ define

```
define <type> @<name>(<type1> %<name>, <type2> %<name>, ...) {...}
; ex: define i32 @foo(i32 %a) {...}
```

+ call

```
call void @<name>(<type1> <operand>, <type2> <operand>, ...)
%<name> = call <ty> @<name>(<type1> <operand>, <type2> <operand>, ...)
; ex: %1 = call i32 @foo(i32 1)
```

+ getelementptr

```
%<name> = getelementptr <type>, ptr %<name> [, <type> <idx>]+
; %my_y_ptr = getelementptr %MyStruct, ptr %my_structs_ptr, i32 2, i32 1
```

+ phi

```
%<name> = phi <type> [<val1> , <label1>], ...
; ex: %y = phi i32 [1, %btrue], [2, %bfalse]
```

+ select

```
%<name> = select i1 <cond>, <type> <operand1>, <type> <operand2>
; ex: %y = select i1 %cond, i32 1, i32 2
```
