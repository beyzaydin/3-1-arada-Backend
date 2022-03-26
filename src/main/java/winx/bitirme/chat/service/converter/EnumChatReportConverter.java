package winx.bitirme.chat.service.converter;

import winx.bitirme.chat.client.model.EnumChartReport;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EnumChatReportConverter implements AttributeConverter<EnumChartReport, Integer> {
    @Override
    public Integer convertToDatabaseColumn(EnumChartReport attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public EnumChartReport convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(EnumChartReport.values())
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
