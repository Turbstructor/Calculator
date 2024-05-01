<p align = "center">
    <img src = "assets-readme/Sparta_Coding_Club.png" width = 150 />
    <img src = "assets-readme/Kotlin_Spring_2nd.png" width = 150 />
</p>

# C a l c u l a t o r
스파르타코딩클럽 내일배움캠프 [`Kotlin` + `Spring`] 트랙 2기 2주차 프로그래밍 기본과제: 계산기 프로그램

---

## 목차
0. [개발 환경](#0-개발-환경)
1. [과제 요구사항](#1-과제-요구사항)
2. [프로그램 흐름도](#2-프로그램-흐름도)
3. [패키지 / 클래스 구조 및 설명](#3-패키지--클래스-구조-및-설명)
   - 3-1. [패키지 구조](#3-1-패키지-구조)
   - 3-2. [클래스 설명](#3-2-클래스-설명)

---

## 0. 개발 환경
| 기준  | 내용                             |
|-----|--------------------------------|
| OS  | `Windows 11 Home 23H2`           |
| IDE | `IntelliJ IDEA 2024.1`           |
| SDK | `OpenJDK 17.0.3` + `Kotlin 1.9.23` |

---

## 1. 과제 요구사항
- [v] `Lv. 1`: 사칙연산(덧셈, 뺄셈, 곱셈, 나눗셈) 연산 수행이 가능한 `Calculator` 클래스 생성, 이걸 이용하여 연산 진행/출력
- [v] `Lv. 2`: `Lv. 1`의 `Calculator`에 나머지 연산 기능 추가
- [v] `Lv. 3`: 사칙연산 기능을 각 연산 당 **별도의 클래스로 분리하여 구현**한 후, `Calculator` 클래스에서 사용
- [v] `Lv. 4`: (선택적) `Lv. 3`에서 만든 연산 클래스들을 `AbstractOperation` 추상 클래스/인터페이스에서 상속/구현하여 사용하는 것으로 변경

---

## 2. 프로그램 흐름도

### 2-1. 기본 흐름

`Main.kt`에 적힌 `main`의 흐름을 의사 코드(pseudocode)로 표현했을 땐 다음과 같습니다:
```pseudocode
아래를 *무한히* 반복:
    사용자의 식 입력을 받는다
    
    만약 "clear"를 입력 받았을 경우
        계산기 메모리를 초기화한다
    그게 아니고 만약 "exit"을 입력 받았을 경우
        반복을 종료한다
    그것도 아닐 경우
        계산기에 식을 보내 계산하게 한다
```

### 2-2. 실행 방법

```
Enter expression: 
```

실행 시 반복적으로 위 메세지로 입력을 물어봅니다. 그러면 사용자는 식을 **다음 둘 중 하나의 방법을 선택**하여 넣어줄 수 있습니다:
1. 피연산자 둘과 연산자 하나로 구성된 식을 **공백 하나로 서로를 구분할 수 있게** 입력
   ```
   Enter expression: 5.0 + 3
   ```
   피연산자로 **정수와 실수 둘 다** 넣어줄 수 있습니다. 단 계산 후 결과값은 **정수로 표현될 수 있을 경우 정수로 표현**합니다:

   ```
   Enter expression: 5.0 + 3
   5 + 3 = 8
   ```
2. **연산자가 먼저 오고 그 다음 피연산자가 오는** 식을 입력
   ```
   Enter expression: * 3
   ```

   이 경우 **계산기 _메모리_ 에 남이있는 (이전 계산) 결과값을 자동으로 좌항 피연산자로 넣어 계산**합니다:
   ```
   Enter expression: 5.0 + 3
   5 + 3 = 8
   
   Enter Expression: * 3.5
   8 * 3.5 = 28
   ```

다음과 같은 경우엔 **오류를 일으킵니다**:
- 연산자 혹은 피연산자로 가공이 불가능한 - 연산자에 사칙연산과 나머지 기호(`+`, `-`, `*`, `/`, `%`)를 제외한 기호가 들어오거나 피연산자에 숫자를 제외한 나머지 - 문자가 들어올 경우
    ```
    Enter expression: 5 & 3
    Wrong expression!
    Wrong operator: &
    ```
    ```
    Enter expression: 2 * PI
    Wrong expression!
    Unparsable expression: 2 * PI
    ```
    - 단 다음과 같이 실수 피연산자에 `f`/`F` 접미사가 붙은 경우 **부동점 실수 리터럴로 인식**하여 오류가 일어나지 않습니다:
        ```
        Enter expression: * 5.0f
        0 * 5 = 0
        ```
    - 이외의 리터럴에 대해선 오류가 일어납니다(실수로 변환하기 때문에 해당 리터럴(`f`, `F`)만 인식함):
        ```
        Enter expression: + 30L
        Wrong expression!
        Unparsable expression: + 30L
        ```

- 나눗셈/나머지 연산 진행 시 우항 피연산자에 0이 들어오는 경우
  ```
  Enter expression: 10 / 0
  Wrong expression!
  Invalid division: cannot divide by zero
  
  Enter expression: 10 % 0
  Wrong expression!
  Invalid division: cannot divide by zero
  ```



식이 아닌 일반 문자열이 들어올 수 있으나, 다음 둘 중 하나가 아니라면(_대소문자 구분 없음_) **오류를 일으킵니다**:
1. `clear`: 계산기 메모리 초기화
   ```
   Enter expression: clear
   Calculator memory has been reset.
   ```
2. `exit`: 프로그램 종료
   ```
   Enter expression: exit
   Shutting down...
   ```
3. 그 외의 (식으로 볼 수 없는) 입력: 분석 오류 메세지 출력
   ```
   Enter expression: reset
   Wrong expression!
   Unparsable expression: reset
   ```

---

## 3. 패키지 / 클래스 구조 및 설명

### 3-1. 패키지 구조
패키지명엔 **모두 소문자로**, 클래스명은 `PascalCase`로 구분 및 식별하였습니다:

![Package Structure](assets-readme/package_structure.png)

- `spartacodingclub.nbcamp.kotlinspring.assignment`: 기본 과제 그룹
  - `core`: 주요 기능들이 속한 패키지
    - `CalculatorRunner`: 계산기 실행을 위한 **`object`** (`Singleton`처럼 사용)
    - `Calculator`: 계산기 시뮬레이션 `class`
    - `operation`: 연산 패키지
      - `AbstractOperation`: 아래 연산 클래스들의 `interface`
      - `ConcreteOperation.kt`: `AbstractOperation` `interface`를 구현한 `class`들이 보관된 곳
        - `AddOperation`: 덧셈 연산 `class`
        - `SubtractOperation`: 뺄셈 연산 `class`
        - `MultiplyOperation`: 곱셈 연산 `class`
        - `DivideOperation`: 나눗셈 연산 `class`
        - `RemainderOperation`: 나머지 연산 `class`
    - `exception`: 계산기 시뮬레이션 사용 중 발생할 수 있는 문제들을 예외로 표현하여 넣어둔 패키지
      - `CustomException.kt`: 계산기 시뮬레이션 사용 중 발생할 수 있는 문제들을 예외로 표현하여 넣어둔 파일
        - `DivisionByZeroException`: 0으로 나눌 때 발생시키는 예외
        - `InvalidOperatorException`: 올바르지 않은 연산자가 들어왔을 때 발생시키는 예외
        - `UnparsableExpressionException`: 계산식을 식별할 수 없을 때 발생시키는 예외

### 3-2. 클래스 설명

#### 3-2-1. `core.CalculatorRunner`
`core.Calculator` 클래스를 사용하여 계산을 진행하는 클래스. `main`으로부터 `execute` 함수를 통해 **수식을 받아 그 결과를 출력하는 과정을 담당**합니다.

##### 3-2-1-1. `CalculationRunner.execute`
```kotlin
fun execute(expressionInput: String)
```
`expressionInput`을 패러미터로 하여, 입력받은 표현식을 (계산한) 결과값과 함께 출력합니다. **최대한 간단하게 출력**하는데, 이는 **정수로 표현될 수 있는 실수값들을 모두 정수로 표현**하는 것을 의미합니다. 과정 중 발생한 예외를 여기서 처리합니다.

의사 코드로 표현한 작동 방식은 다음과 같습니다:
```pseudocode
다음 과정 중 발생한 예외를 처리한다:
    입력받은 표현식을 `<좌항 피연산자> <연산자> <우항 피연산자>` 형식에 맞게 바꾼다
    위 표현식을 argument로 넘겨 `Calculator` 인스턴스를 생성한다

    계산을 진행한다
    메모리에 계산 결과를 저장한다
    
    계산식을 `<계산식> = <결과(메모리)>` 형식에 맞게, 간략화하여 바꾼다
    계산식을 출력한다
예외 발생 시:
    예외에 따른 오류 메세지를 출력한다
```

과정 중 표현식을 형식에 맞게 바꾸는 부분에 내부적으로 `getFullExpression` 함수와 `getFullExpressionWithResultsSimplified` 함수를 사용합니다.

###### 3-2-1-1-1. `CalculationRunner.getFullExpression`
```kotlin
private fun getFullExpression(expression: String): String
```
입력받은 표현식을 `<좌항 피연산자> <연산자> <우항 피연산자>` 형식에 맞춰 바꾼 다음 반환합니다. 이는 입력받은 표현식이 **연산자와 그 뒤에 붙는 피연산자의 형식**일 경우 그 역할이 유의미한데, 이 경우 **`CalculatorRunner.memory` 값을 좌항 피연산자로 넣어줍니다.** 연산자 양옆으로 피연산자가 들어오는 경우엔 표현식을 그대로 반환합니다.

앞의 두 형식 중 하나로 분류할 수 없을 경우 - 여기선 간단하게 **공백을 기준으로 갈라지는 조각 갯수를 가지고 판단**합니다 - **`UnparsableExpressionException` 예외를 발생**시킵니다.

###### 3-2-1-1-2. `CalculationRunner.getSimplifiedNumber`
```kotlin
private fun getSimplifiedNumber(value: Double): Number
```
주어진 실수값(`value`)을 **정수로 표현할 수 있을 경우** 정수값을, 그렇지 않을 경우 실수값을 반환합니다.

###### 3-2-1-1-3. `CalculationRunner.getFullExpressionWithResultsSimplified`
```kotlin
private fun getFullExpressionWithResultSimplified(expression: String): String
```
`CalculatorRunner.execute`를 통해 입력받은 표현식과 그 계산 결과값을 하나로 합친 식을 반환합니다. 이 때 **정수로 표현될 수 있는 값들은 모두 정수로 변환**하여 식을 간략화합니다.

##### 3-2-1-2. `CalculationRunner.clearMemory`
```kotlin
fun clearMemory()
```
저장되어 있는 계산 결과를 초기화합니다. `main`에서 실행할 때 수식으로 `clear` 문자열이 들어왔을 경우 호출됩니다.


#### 3-2-2. `core.Calculator`
피연산자 - 연산자(에 해당하는 모듈) - 결과값 조합이 하나로 묶인 클래스. `CalculatorRunner`에서 새로운 계산이 실행될 때 마다 **새 인스턴스를 만들어 사용**합니다.

##### 3-2-2-1. `Calculator`의 생성자
(입력받은) 표현식을 패러미터로 하여 인스턴스 생성 시 계산을 위한 값을 가공하여 프로퍼티들에 씌우는데, 의사 코드로 표현한 작동 방식은 다음과 같습니다:
```pseudocode
표현식을 3조각으로 쪼갠다(3조각으로 쪼개진다고 *전제*한다)
좌항/우항 피연산자에 각각 조각들 중 0번/2번 인덱스의 값을 넣는다
조각들 중 1번 연산자에 대하여 다음를 연산자에 넣는다:
    사칙연산과 나머지 연산 기호(`+`, `-`, `*`, `/`, `%`): 각각에 대응하는 `Operation` 인스턴스
    그 외의 값: `InvalidOperatorException` 예외 생성
```

##### 3-2-2-2. `Calculator.calculate`
```kotlin
fun calculate()
```
가지고 있는 연산 모듈과 피연산자들로부터 결과값을 계산하여 `result` 프로퍼티에 넣습니다.


#### 3-2-3. `core.operation`
계산기 시뮬레이터의 연산 모듈로 사용되는 패키지입니다. 기본 틀(인터페이스) `AbstractOperation`을 기반으로 5개 연산 - 사칙연산과 나머지 연산 - 에 대한 연산자 클래스가 소속되어 있습니다.
- `AbstractOperation`: 연산 모듈 인터페이스. `operate`로 결과값을 계산하는 틀을 제공합니다.
- `AddOperation`: 덧셈 연산 모듈
- `SubtractOperation`: 뺄셈 연산 모듈
- `MultiplyOperation`: 곱셈 연산 모듈
- `DivideOperation`: 나눗셈 연산 모듈
- `RemainderOperation`: 나머지 연산 모듈

이 중 `DivideOperation`과 `RemainderOperation`에선 `operate`의 패러미터 `b`에 값 `0`이 들어갈 경우 `DivisionByZeroException` 예외를 발생시킵니다.

#### 3-2-4. `core.exception`
표현식을 가공하고 계싼하면서 발생하는 오류들을 정의하는 예외들이 들어가 있는 패키지입니다. 모두 **올바르지 않은 값을 넣음으로써 발생**하기에, `IllegalArgumentException`으로부터 상속받았습니다.
- `DivisionByZeroException`: 나눗셈 혹은 나머지 연산을 진행할 때 우항 피연산자가 0일 경우 발생하는 예외입니다.
- `InvalidOperatorException`: 연산자로 인식할 수 없는 - 사칙연산과 나머지 연산(`+`, `-`, `*`, `/`, `%`)을 제외한 나머지 기호가 들어왔을 경우 - 발생하는 예외입니다.
- `UnparsableExpressionException`: 표현식이 가공할 수 없는 형식 - 공백을 기준으로 나누었을 때 2~3개 묶음이 나오지 않는 식 - 으로 들어왔을 경우 발생하는 예외입니다.