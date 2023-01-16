# BigDecimal 데이터를 Json Serialize



# 참고자료

[Java to Jackson JSON serialization: Money fields - Stack Overflow](https://stackoverflow.com/questions/11319445/java-to-jackson-json-serialization-money-fields) <br>

<br>

# 설명

- jackson-databind 에서 제공하는 클래스인 `JsonSerializer` 를 확장(상속)한 클래스를 생성한다.
- 그리고 이 클래스의 abstract 매서드를 오버라이딩한다.
- BigDecimal 을 String으로 어떻게 변환할지 구현해준다.
- Response 객체에서는 `@JsonSerialize(using = 클래스명.class)` 어노테이션을 통해 Serializer 를 어떤 것을 사용할지를 명시해준다.

<br>

# 예제

**PriceSerializer.java**<br>

> BigDecimal 객체를 String으로 변환해주는 객체다. Jackson databind 의 기본옵션 대신 이것을 사용하도록 지정해서, 커스텀한 동작을 하도록 해줄 수 있다.

<br>

```java
package io.stock.kr.calculator.common.number;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 3자리수 이하 버림으로 세팅
        gen.writeString(
            value.setScale(3, RoundingMode.CEILING).toString()
        );
    }

}
```

<br>

**Response 용도 POJO 코드**<br>

**StockPriceResponse.java**<br>

> Json Response 용도의 객체.

```java
package io.stock.kr.calculator.stock.price.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.stock.kr.calculator.common.number.PriceSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@ToString
public class StockPriceResponse {
    private final String ticker;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @JsonProperty("open")
    @JsonSerialize(using = PriceSerializer.class)
    private final BigDecimal open;

    @JsonProperty("high")
    @JsonSerialize(using = PriceSerializer.class)
    private final BigDecimal high;

    @JsonProperty("low")
    @JsonSerialize(using = PriceSerializer.class)
    private final BigDecimal low;

    @JsonProperty("close")
    @JsonSerialize(using = PriceSerializer.class)
    private final BigDecimal close;

    @Builder
    public StockPriceResponse(
        String ticker, LocalDate date,
        BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close
    ){
        this.ticker = ticker;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }
}
```



