package com.app.lizhilives.utils;


public class StausCodeUtil {
    static String code_400 = "(錯誤請求)服務器不理解請求的語法。";
    static String code_401 = "(未授權)請求要求身份驗證。對於需要登錄的網頁，服務器可能返回此響應。";
    static String code_403 = "(禁止)服務器拒絕請求。";
    static String code_404 = "(未找到)服務器找不到請求的網頁。";
    static String code_405 = "(方法禁用)禁用請求中指定的方法。";
    static String code_406 = "(不接受)無法使用請求的內容特性響應請求的網頁。";
    static String code_407 = "(需要代理授權)此狀態代碼與 401（未授權）類似，但指定請求者應當授權使用代理。";
    static String code_408 = "(請求超時) 服務器等候請求時發生超時。";
    static String code_409 = "(沖突) 服務器在完成請求時發生沖突。 服務器必須在響應中包含有關沖突的信息。";
    static String code_410 = "(已刪除) 如果請求的資源已永久刪除，服務器就會返回此響應。";
    static String code_411 = "(需要有效長度)服務器不接受不含有效內容長度標頭字段的請求。";
    static String code_412 = "(未滿足前提條件)服務器未滿足請求者在請求中設置的其中壹個前提條件。";
    static String code_413 = "(請求實體過大)服務器無法處理請求，因為請求實體過大，超出服務器的處理能力。";
    static String code_414 = "(請求的 URI 過長)請求的 URI（通常為網址）過長，服務器無法處理。";
    static String code_415 = "(不支持的媒體類型)請求的格式不受請求頁面的支持。";
    static String code_416 = "(請求範圍不符合要求)如果頁面無法提供請求的範圍，則服務器會返回此狀態代碼。";
    static String code_417 = "(未滿足期望值)服務器未滿足”期望”請求標頭字段的要求。";

    static String code_500 = "(服務器內部錯誤) 服務器遇到錯誤，無法完成請求。";
    static String code_501 = "(尚未實施)服務器不具備完成請求的功能。 例如，服務器無法識別請求方法時可能會返回此代碼。";
    static String code_502 = "(錯誤網關)服務器作為網關或代理，從上遊服務器收到無效響應。";
    static String code_503 = "(服務不可用)服務器目前無法使用（由於超載或停機維護）。 通常，這只是暫時狀態。";
    static String code_504 = "(網關超時) 服務器作為網關或代理，但是沒有及時從上遊服務器收到請求。";
    static String code_505 = "(HTTP版本不受支持)服務器不支持請求中所用的 HTTP 協議版本。";
    static String[] stausCodes = {code_400, code_401, code_403, code_404, code_405, code_406, code_407,
            code_408, code_409, code_410, code_411, code_412, code_413, code_414, code_415, code_416, code_417,
            code_500, code_501, code_502, code_503, code_504, code_505
    };
    static int[] stausCodei = {400, 401, 403, 404, 405, 406, 407,
            408, 409, 410, 411, 412, 413, 414, 415, 416, 417,
            500, 501, 502, 503, 504, 505
    };

    public static String getStausCode(int stausCode) {
        String string = "與服務器通訊失敗，請稍後再試。。。或檢查網絡！";
        for (int i = 0; i < stausCodei.length; i++) {
            if (stausCode == stausCodei[i]) {
                string = stausCodes[i];
                break;
            }
        }
        return "[" + stausCode + "]" + string;
    }

}
