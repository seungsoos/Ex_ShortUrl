package org.example.shorturl.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Base62UtilTest {

    Base62Util base62Util = new Base62Util();

    @Test
    @DisplayName("shortUrl의 길이가 8자리 이내여야한다.")
    void encodeLength() {
        // given-when
        String encode = base62Util.encode(1L);

        // then
        assertThat(encode).hasSizeLessThan(9);
    }

    @Test
    @DisplayName("입력받은 id를 정상적으로 Base62로 인코딩한다.")
    void encodeSuccess() {
        // given
        List<Long> ids = List.of(1L, 2L, 3L);
        List<String> encodes = List.of("AAAAAAAB", "AAAAAAAC", "AAAAAAAD");
        List<String> results = new ArrayList<>();
        
        // when
        for (int i = 0; i < ids.size(); i++) {
             results.add(base62Util.encode(ids.get(i)));
        }
        
        // then
        assertThat(results).containsAll(encodes);
    }

}