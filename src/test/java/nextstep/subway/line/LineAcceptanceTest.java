package nextstep.subway.line;

import static nextstep.subway.line.LineAcceptanceStep.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.line.dto.LineRequest;

@DisplayName("지하철 노선 관련 기능")
public class LineAcceptanceTest extends AcceptanceTest {
    private static final LineRequest 신분당선 = new LineRequest("신분당선", "bg-red-600");
    private static final LineRequest 구분당선 = new LineRequest("구분당선", "bg-blue-600");

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(신분당선);
        // then
        지하철_노선_생성됨(response);
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    void createLine2() {
        // given
        지하철_노선_등록되어_있음(신분당선);
        // when
        ExtractableResponse<Response> response = 지하철_노선_생성_요청(신분당선);
        // then
        지하철_노선_생성_실패됨(response);
    }

    @DisplayName("지하철 노선 목록을 조회한다.")
    @Test
    void getLines() {
        // given
        Long createdLineId1 = 지하철_노선_등록되어_있음(신분당선);
        Long createdLineId2 = 지하철_노선_등록되어_있음(구분당선);

        // when
        // 지하철_노선_목록_조회_요청
        ExtractableResponse<Response> response = 지하철_노선_목록_조회_요청();

        // then
        지하철_노선_목록_응답됨(response);
        지하철_노선_목록_포함됨(createdLineId1, createdLineId2, response);
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void getLine() {
        // given
        Long createdLineId = 지하철_노선_등록되어_있음(신분당선);
        // when
        ExtractableResponse<Response> response = 지하철_노선_조회_요청(createdLineId);
        // then
        지하철_노선_응답됨(response);
    }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        // given
        Long createdLineId = 지하철_노선_등록되어_있음(신분당선);
        // when
        ExtractableResponse response = 지하철_노선_수정_요청(createdLineId, 구분당선);
        // then
        지하철_노선_수정됨(response);
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    void deleteLine() {
        // given
        // 지하철_노선_등록되어_있음
        Long createdLineId = 지하철_노선_등록되어_있음(신분당선);
        // when
        ExtractableResponse<Response> response = 지하철_노선_제거_요청(createdLineId);
        // then
        지하철_노선_삭제됨(response);
    }
}
