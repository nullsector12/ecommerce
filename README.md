## 시퀀스 다이어그램
#### 상품/지갑
![e-commerce 시퀀스다이어그램 - 상품/지갑](https://github.com/nullsector12/ecommerce/blob/master/product.png)

#### 주문/결제
![e-commerce 시퀀스다이어그램 - 주문/결제](https://github.com/nullsector12/ecommerce/blob/master/order.png)

#### 상위상품/장바구니
![e-commerce 시퀀스다이어그램 - 상위상품/장바구니](https://github.com/nullsector12/ecommerce/blob/master/cart.png)

## 프로젝트 마일스톤
### [마일스톤](https://github.com/users/nullsector12/projects/1)

## ERD
### ![e-commerce ERD](https://github.com/nullsector12/ecommerce/blob/master/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-07-19%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%202.50.51.png)

## API 명세서
### [e-commerce API명세서](https://docs.google.com/spreadsheets/d/1JDhCTSviH_lqee0DRvM07r79nLcOu0u1uzkMXx_34ME/edit?usp=sharing)

## 
## 챕터 2 이커머스 서버구축 회고 
##
  일단 결론부터 말하자면 쉽지 않았다. 
  일을 하며 한번도 작성해본 적 없는 시퀀스 다이어그램과 같은 설계 문서를 작성하는 것도 쉽지 않았고,
  하려고 많이 시도했었지만 결국 현업이 바쁘다, 피곤하다는 핑계로 미루고 미뤘던 온전한 비즈니스 서비스 하나를 처음부터 끝까지 개발해보기,
  이건 우리 기수 모든 분들이 그렇겠지만 현업의 일정을 회사에 피해없이 소화하면서도 남은 시간을 짜내서 과제를 해결해야한다는 부담감,
  절대적인 시간이 부족하니 잠을 줄여 3~4시간 자거나 그 주에 현생이슈가 있었다면 그 부족한 시간을 밤샘으로 때우고 아침에 출근하면서 깎여가는 체력과 정신력,
  결국 시간이 부족하고 급해지니 공부하러 와서 기껏 배운 TDD는 뒷전이 되고 과제 해결을 위한 로직부터 먼저 구현하고 있는 모습도 다시 확인했고... 쉬운게 하나도 없었다.
  그래도 완벽하진 않지만 프로젝트 하나를 만들고 나니 다음에는 이렇게 해야겠다 하는 나름의 기준이 생긴 것 같다.
  그리고 TDD는 좀 더 연습해야 할 것 같다. 이건 뭐 익숙해지지를 않는다. 테스트 코드 만드는게 더 어려움...
  하지만 테스트코드를 작성한 덕분에 로직상 에러도 사전에 쉽게 찾는 경험도 하고, 비즈니스 로직을 구현하기 전에 미리 예외상황에 대해 생각하고 로직을 작성하는 경험도 해서
  왜 TDD를 쓰는지는 알 수 있었다. 시간이 모자르기 전 까지는...
  챕터 2도 곧 끝나고 이제 10주 과정의 딱 절반 왔는데 남은 5주간 꺾이지 않고 끝까지 잘 마무리하고 싶다. 

---

## 챕터 3. STEP 11/12 동시성 제어
### [동시성 제어 과제링크](https://velog.io/@nullsector/%ED%95%AD%ED%95%B4%ED%94%8C%EB%9F%AC%EC%8A%A4-%EB%B0%B1%EC%97%94%EB%93%9C-5%EA%B8%B0-Chapter3.-%EB%8B%A4%EC%96%91%ED%95%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%EB%B2%95-%EC%8B%9C%EB%8F%84)
