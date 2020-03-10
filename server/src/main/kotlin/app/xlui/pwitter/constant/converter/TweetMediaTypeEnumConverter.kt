package app.xlui.pwitter.constant.converter

import app.xlui.pwitter.constant.TweetMediaTypeEnum
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class TweetMediaTypeEnumConverter : AttributeConverter<TweetMediaTypeEnum, Int> {
    override fun convertToDatabaseColumn(attribute: TweetMediaTypeEnum) = attribute.code

    override fun convertToEntityAttribute(dbData: Int) = TweetMediaTypeEnum.of(dbData)
}