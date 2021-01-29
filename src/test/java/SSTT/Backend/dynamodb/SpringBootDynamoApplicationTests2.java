package SSTT.Backend.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SpringBootDynamoApplicationTests2 {
    private static final String TABLE_NAME = "test";

    @Autowired
    private AmazonDynamoDB dynamoDB;

    @BeforeEach
    public void 테이블생성() {
        dynamoDB.createTable(new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withAttributeDefinitions(Arrays.asList(
                        new AttributeDefinition("id", ScalarAttributeType.S)
                ))
                .withKeySchema(Arrays.asList(
                        new KeySchemaElement("id", KeyType.HASH)
                ))
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L))
        );
    }

    @Test
    public void 아이템_삽입_후_스캔_데이터_확인_성공() {
        // given
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", new AttributeValue().withS("1"));
        item.put("name", new AttributeValue().withS("wonchul"));
        item.put("birth_year", new AttributeValue().withN("1992"));

        // when
        dynamoDB.putItem(new PutItemRequest().withTableName(TABLE_NAME).withItem(item));

        // then
        ScanResult result = dynamoDB.scan(new ScanRequest(TABLE_NAME));
        assertThat(result.getCount()).isEqualTo(1);
        assertThat(result.getItems().get(0).get("id").getS()).isEqualTo("1");
        assertThat(result.getItems().get(0).get("name").getS()).isEqualTo("wonchul");
        assertThat(result.getItems().get(0).get("birth_year").getN()).isEqualTo("1992");
    }

    @Test
    public void 아이템_삽입_후_해당_데이터_검색_확인_성공() {
        // given
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", new AttributeValue().withS("1"));
        item.put("name", new AttributeValue().withS("wonchul"));
        item.put("birth_year", new AttributeValue().withN("1992"));
        dynamoDB.putItem(new PutItemRequest().withTableName(TABLE_NAME).withItem(item));

        // when
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", new AttributeValue().withS("1"));
        GetItemResult result = dynamoDB.getItem(new GetItemRequest().withTableName(TABLE_NAME).withKey(key));

        // then
        assertThat(result).isNotNull();
        AssertionsForInterfaceTypes.assertThat(result.getItem()).isNotNull();
        assertThat(result.getItem().get("id").getS()).isEqualTo("1");
        assertThat(result.getItem().get("name").getS()).isEqualTo("wonchul");
        assertThat(result.getItem().get("birth_year").getN()).isEqualTo("1992");
    }

    @Test
    public void 아이템_삽입_후_해당_데이터_수정_성공() {
        // given
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", new AttributeValue().withS("1"));
        item.put("name", new AttributeValue().withS("wonchul"));
        item.put("birth_year", new AttributeValue().withN("1992"));
        dynamoDB.putItem(new PutItemRequest().withTableName(TABLE_NAME).withItem(item));

        // when
        Map<String, AttributeValue> updateKey = new HashMap<>();
        updateKey.put("id", new AttributeValue().withS("1"));

        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("#N", "name");
        nameMap.put("#BY", "birth_year");

        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":name", new AttributeValue().withS("WONCHUL"));
        valueMap.put(":birth_year", new AttributeValue().withN("1992"));
        dynamoDB.updateItem(
                new UpdateItemRequest()
                        .withTableName(TABLE_NAME)
                        .withKey(updateKey)
                        .withUpdateExpression("set #N = :name, #BY = :birth_year")
                        .withExpressionAttributeNames(nameMap)
                        .withExpressionAttributeValues(valueMap)
        );

        // then
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", new AttributeValue().withS("1"));
        GetItemResult result = dynamoDB.getItem(new GetItemRequest().withTableName(TABLE_NAME).withKey(key));

        assertThat(result).isNotNull();
        AssertionsForInterfaceTypes.assertThat(result.getItem()).isNotNull();
        AssertionsForInterfaceTypes.assertThat(result.getItem()).isNotNull();
        assertThat(result.getItem().get("id").getS()).isEqualTo("1");
        assertThat(result.getItem().get("name").getS()).isEqualTo("WONCHUL");
        assertThat(result.getItem().get("birth_year").getN()).isEqualTo("1992");
    }

    @Test
    public void 아이템_삽입_후_해당_데이터_삭제_성공() {
        // given
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", new AttributeValue().withS("1"));
        item.put("name", new AttributeValue().withS("wonchul"));
        item.put("birth_year", new AttributeValue().withN("1992"));
        dynamoDB.putItem(new PutItemRequest().withTableName(TABLE_NAME).withItem(item));

        // when
        Map<String, AttributeValue> deleteKey = new HashMap<>();
        deleteKey.put("id", new AttributeValue().withS("1"));
        dynamoDB.deleteItem(new DeleteItemRequest().withTableName(TABLE_NAME).withKey(deleteKey));

        // then
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", new AttributeValue().withS("1"));
        GetItemResult result = dynamoDB.getItem(new GetItemRequest().withTableName(TABLE_NAME).withKey(key));

        assertThat(result).isNotNull();
        AssertionsForInterfaceTypes.assertThat(result.getItem()).isNull();
    }


    @AfterEach
    public void clear() {
        dynamoDB.deleteTable("test");
    }
}
