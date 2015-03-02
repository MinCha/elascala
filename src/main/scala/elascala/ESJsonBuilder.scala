package elascala

import elascala.ESJsonExpression._

/**
 * Created by elvin on 15. 3. 2..
 */
object ESJsonBuilder {
  def update(keyValues: (String, Any)*): UpdateJsonBuilder = {
    new UpdateJsonBuilder().params(keyValues:_*)
  }
}

class UpdateJsonBuilder {
  private var parameters: Array[(String, Any)] = Array[(String, Any)]()
  private var upsertParameters: Array[(String, Any)] = Array[(String, Any)]()

  def params(parameters: (String, Any)*): UpdateJsonBuilder = {
    this.parameters = parameters.toArray
    this
  }

  def upsert(parameters: (String, Any)*): UpdateJsonBuilder = {
    this.upsertParameters = parameters.toArray
    this
  }

  def build: String = {
    val update = for (x <- parameters) yield UpdateScriptFragment.format(x._1, wrap(x._2))
    val upsert = for (x <- upsertParameters) yield UpdateUpsertFragment.format(x._1, wrap2(x._2))
    UpdateTemplate.format(update.mkString(";"), upsert.mkString(","))
  }
}