/**
 * arithmetic.c
 * COSC326 Etude 3
 * Arithmetic
 *
 * @author Rhianne Price
 * April 2017
 **/
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define PLUS 0
#define TIMES 1

/**
 * Memory allocation with error checking, exits if memory allocation fails.
 */
void *emalloc(size_t s){
  void *result = malloc(s);
  if(NULL == result){
    fprintf(stderr, "Memory allocation of failed\n");
    exit(EXIT_FAILURE);
  }
  return result;
}
/**
 * Given a set of numbers, operations, and a target value, this function prints
 * an expression using all of those numbers and operations in the order given,
 * that evaluatesto the target when read from left to right, and returns 1.
 * If the expression does not evaluate to the target, it returns 0;
 */
int solve_left_to_right(int *numbers, int size, int *operators, int total) {
  int i, j;
  int first = numbers[0];
  int sum = first;

  for(i = 0; i < size - 1; i++) {
    if(operators[i] == PLUS) {
      sum += numbers[i + 1];
    } else if(operators[i] == TIMES){
      sum *= numbers[i + 1];
    }
    if(i == size - 2 && sum == total) {
      printf("L %d", first);
      for(j = 0; j <= i; j++){
        printf(" %c %d", operators[j] == PLUS ? '+' : '*', numbers[j + 1]);
      }
      printf("\n");
      return 1;
    }
  }
  return 0;
}

/**
 * Given a set of numbers, operations, and a target value, this function prints
 * an expression using all of those numbers and operations in the order given,
 * that evaluates to the target when multiplication preceeds addition,
 * and returns 1. If the expression does not evaluate to the target,
 * it returns 0;
 */
int solve_normally(int *numbers, int size, int *operators, int total) {
  int i, j, sum;
  int first = numbers[0];
  int *working = emalloc(size * sizeof working);

  working[0] = first;
  for(i = 0; i < size - 1; i++) {
    working[i + 1] = numbers[i + 1];
    if(operators[i] == TIMES) {
      working[i + 1] = numbers[i + 1] * working[i];
      working[i] = 0;
    }
  }
  sum = working[0];

  for(i = 1; i < size; i++) {
      sum += working[i];
  }

  if(sum == total) {
    printf("N %d", first);
    for(j = 0; j < size - 1; j++){
      printf(" %c %d", operators[j] == PLUS ? '+' : '*', numbers[j + 1]);
    }
      printf("\n");
      free(working);
      return 1;
    }
free(working);
return 0;
}

/**
 * Scans in numbers to be used in the expression, target value, and order of
 * operations from stdin. For each scenario, all the possible combinations of
 * + and * are passed to either of the two solver methods to check for and print
 * solutions.
 */
int main() {
  int maxSize = 25;
  int *numbers = emalloc(25 * sizeof numbers);
  int number, total, bit, size, possible_combos, i, solutions;
  char order = '\0';
  int *operators;

  while(!feof(stdin)){
    i = 0;
    solutions = 0;
    while(1 == scanf("%d", &number)){
      numbers[i++] = number;
      if(i == maxSize){
        maxSize *= 2;
        numbers = realloc(numbers, maxSize * sizeof numbers);
      }
    }
    total = numbers[size = i - 1];
    scanf("%c\n", &order);

    if(size == 1 && numbers[0] == total){
      printf("%c %d\n", order, total);
      continue;
    }

    possible_combos = pow(2, size - 1);
    operators = emalloc((size - 1) * sizeof operators);
    for(i = 0; i < possible_combos; i++) {
      for(bit = 0; bit < size - 1; bit++) {
        operators[bit] = (i >> bit) & 1;
      }
      if(order == 'N') {
        solutions += solve_normally(numbers, size, operators, total);
        if(solutions == 1) break;
      } else if (order == 'L'){
        solutions += solve_left_to_right(numbers, size, operators, total);
        if(solutions == 1) break;
      }
    }
    if(solutions == 0) {
      printf("%c impossible\n", order);
    }
    free(operators);
  }
  free(numbers);
  return EXIT_SUCCESS;
}
