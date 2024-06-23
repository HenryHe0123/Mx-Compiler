# Mx Compiler

This is an experimental compiler that compiles Mx* programming language into RISCV-32I assembly and supports several
optimizations below. See the
complete [project introduction](https://github.com/ACMClassCourses/Compiler-Design-Implementation) here.

### Progress

- [x] Lexer & Parser (by ANTLR)
- [x] AST Node Design
- [x] AST Builder
- [x] Semantic Checker
- [x] Pass Semantic Test
- [x] IR Design
- [x] IR Builder
- [x] Pass IR Test
- [x] Assembly Generation
- [x] Pass Assembly Test

### Optimization

- [x] Constant Folding (local)
- [x] Global2Local
- [x] Mem2Reg (including SSA destruction)
- [x] Dead Code Elimination
- [x] Constant Propagation
- [x] Register Allocation (a simple graph coloring)
- [x] Block Merging
- [x] Peephole Optimization
