package com.example.data.repository

import com.example.data.db.RechargeDao
import com.example.data.model.Coupon
import com.example.data.model.Operator
import com.example.data.model.PlanCategory
import com.example.data.model.RechargePlan
import com.example.data.model.RechargeTransaction
import com.example.data.model.TelecomCircles
import kotlinx.coroutines.flow.Flow
import java.util.UUID

data class SavedContact(
    val name: String,
    val number: String,
    val operator: Operator,
    val circle: String
)

data class DthOperator(
    val id: String,
    val name: String,
    val sampleId: String,
    val minAmount: Int,
    val popularPlans: List<DthPlan>
)

data class DthPlan(
    val name: String,
    val price: Int,
    val validity: String,
    val channels: String,
    val description: String
)

class RechargeRepository(private val rechargeDao: RechargeDao) {

    val allTransactions: Flow<List<RechargeTransaction>> = rechargeDao.getAllTransactions()
    val recentTransactions: Flow<List<RechargeTransaction>> = rechargeDao.getRecentTransactions(5)

    suspend fun saveTransaction(transaction: RechargeTransaction): Long {
        return rechargeDao.insertTransaction(transaction)
    }

    suspend fun clearHistory() {
        rechargeDao.clearAll()
    }

    // Common Indian Contacts for quick 1-tap select
    val defaultContacts = listOf(
        SavedContact("Self (Primary)", "9820123456", Operator.JIO, "Mumbai"),
        SavedContact("Maa", "9811234567", Operator.AIRTEL, "Delhi NCR"),
        SavedContact("Papa", "9711345678", Operator.JIO, "Delhi NCR"),
        SavedContact("Bhai", "9555456789", Operator.VI, "UP East"),
        SavedContact("Home Landline/BSNL", "9412567890", Operator.BSNL, "Maharashtra & Goa")
    )

    // Available discount coupons
    val availableCoupons = listOf(
        Coupon(
            code = "BHARAT50",
            discountDescription = "Flat ₹50 Cashback on recharges ₹199+",
            minAmount = 199,
            discountAmount = 50
        ),
        Coupon(
            code = "UPI10",
            discountDescription = "10% Instant Discount on UPI payment",
            minAmount = 249,
            discountAmount = 30,
            isPercentage = true,
            percentValue = 0.10,
            maxDiscount = 35
        ),
        Coupon(
            code = "SUPER5G",
            discountDescription = "Flat ₹30 off on Unlimited 5G packs",
            minAmount = 349,
            discountAmount = 30
        ),
        Coupon(
            code = "SAVER20",
            discountDescription = "Flat ₹20 discount on any recharge",
            minAmount = 149,
            discountAmount = 20
        )
    )

    fun detectOperator(mobileNumber: String): Pair<Operator, String> {
        val cleanNumber = mobileNumber.filter { it.isDigit() }.takeLast(10)
        if (cleanNumber.length < 2) return Pair(Operator.JIO, "Delhi NCR")

        val prefix2 = cleanNumber.take(2).toIntOrNull() ?: 98
        val prefix3 = cleanNumber.take(3).toIntOrNull() ?: 982

        return when {
            prefix2 in 60..69 -> Pair(Operator.JIO, "Delhi NCR")
            prefix2 in 70..73 -> Pair(Operator.JIO, "Mumbai")
            prefix2 in 74..76 -> Pair(Operator.AIRTEL, "Karnataka")
            prefix2 in 77..79 -> Pair(Operator.JIO, "Gujarat")
            prefix2 in 80..85 -> Pair(Operator.AIRTEL, "Maharashtra & Goa")
            prefix2 in 86..89 -> Pair(Operator.VI, "UP East")
            prefix2 in 90..91 -> Pair(Operator.VI, "Bihar & Jharkhand")
            prefix2 in 92..94 -> Pair(Operator.BSNL, "Rajasthan")
            prefix2 in 95..96 -> Pair(Operator.VI, "Delhi NCR")
            prefix2 == 97 -> Pair(Operator.JIO, "Delhi NCR")
            prefix2 == 98 -> {
                if (prefix3 in 981..984) Pair(Operator.AIRTEL, "Delhi NCR")
                else Pair(Operator.AIRTEL, "Mumbai")
            }
            prefix2 == 99 -> Pair(Operator.AIRTEL, "Punjab & Haryana")
            else -> Pair(Operator.JIO, "Delhi NCR")
        }
    }

    fun getAllPlans(operator: Operator): List<RechargePlan> {
        return when (operator) {
            Operator.JIO -> jioPlans
            Operator.AIRTEL -> airtelPlans
            Operator.VI -> viPlans
            Operator.BSNL -> bsnlPlans
        }
    }

    private val jioPlans = listOf(
        // Popular / 5G
        RechargePlan(
            id = "jio_349",
            operator = Operator.JIO,
            price = 349,
            validity = "28 Days",
            data = "2 GB/Day + True 5G",
            perks = listOf("Unlimited True 5G", "JioCinema", "JioTV", "JioCloud 50GB"),
            category = PlanCategory.POPULAR,
            isBestSeller = true,
            promoTag = "BESTSELLER",
            description = "Unlimited 5G data with 2GB 4G data/day, truly unlimited voice calls, and 100 SMS/day."
        ),
        RechargePlan(
            id = "jio_299",
            operator = Operator.JIO,
            price = 299,
            validity = "28 Days",
            data = "1.5 GB/Day",
            perks = listOf("Truly Unlimited Calls", "JioTV", "JioCinema"),
            category = PlanCategory.DAILY_DATA,
            isBestSeller = true,
            promoTag = "TRENDING",
            description = "1.5 GB high-speed daily data, unlimited calls to all networks in India, 100 SMS/day."
        ),
        RechargePlan(
            id = "jio_399",
            operator = Operator.JIO,
            price = 399,
            validity = "28 Days",
            data = "2.5 GB/Day + True 5G",
            perks = listOf("Unlimited 5G", "JioCinema Premium (ad-free)", "JioTV"),
            category = PlanCategory.TRUE_5G,
            promoTag = "5G HERO",
            description = "High bandwidth plan for power users with ad-free entertainment bundle and unlimited 5G."
        ),
        RechargePlan(
            id = "jio_666",
            operator = Operator.JIO,
            price = 666,
            validity = "70 Days",
            data = "1.5 GB/Day",
            perks = listOf("Truly Unlimited Calls", "JioTV", "JioCinema"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "VALUE PACK",
            description = "70 days long validity pack with 1.5GB/day data and full benefits."
        ),
        RechargePlan(
            id = "jio_899",
            operator = Operator.JIO,
            price = 899,
            validity = "90 Days",
            data = "2 GB/Day + 20GB Extra",
            perks = listOf("Unlimited True 5G", "20 GB Bonus Data", "JioCinema", "JioTV"),
            category = PlanCategory.LONG_VALIDITY,
            isBestSeller = true,
            promoTag = "90 DAYS + 20GB",
            description = "Quarterly plan with 2GB/day + 20GB extra data voucher, unlimited 5G access."
        ),
        RechargePlan(
            id = "jio_3599",
            operator = Operator.JIO,
            price = 3599,
            validity = "365 Days (1 Year)",
            data = "2.5 GB/Day + True 5G",
            perks = listOf("Unlimited True 5G 365 Days", "JioCinema", "JioTV", "Priority Customer Care"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "ANNUAL 365 DAYS",
            description = "Zero recharge worries for an entire year. Unlimited 5G with 2.5GB/day and 100 SMS/day."
        ),
        RechargePlan(
            id = "jio_148",
            operator = Operator.JIO,
            price = 148,
            validity = "28 Days",
            data = "10 GB Total",
            voice = "Data Only (No Voice)",
            sms = "No SMS",
            perks = listOf("12 OTT Apps (SonyLIV, ZEE5, JioCinema)", "10 GB High Speed Data"),
            category = PlanCategory.ENTERTAINMENT,
            promoTag = "12 OTT APPS",
            description = "Includes subscription to SonyLIV, ZEE5, Lionsgate Play, Discovery+, Sun NXT, etc."
        ),
        RechargePlan(
            id = "jio_29",
            operator = Operator.JIO,
            price = 29,
            validity = "1 Day",
            data = "2 GB Data",
            voice = "No Voice",
            sms = "No SMS",
            perks = listOf("Instant Booster"),
            category = PlanCategory.DATA_BOOSTER,
            description = "Add 2GB high speed 4G/5G data instantly to existing plan."
        ),
        RechargePlan(
            id = "jio_69",
            operator = Operator.JIO,
            price = 69,
            validity = "Active Plan",
            data = "6 GB Data Booster",
            voice = "No Voice",
            sms = "No SMS",
            perks = listOf("High Speed Data"),
            category = PlanCategory.DATA_BOOSTER,
            description = "6 GB data booster valid till existing base plan validity."
        ),
        RechargePlan(
            id = "jio_100",
            operator = Operator.JIO,
            price = 100,
            validity = "Unlimited",
            data = "None",
            voice = "₹81.75 Talktime",
            sms = "Standard rates",
            perks = listOf("Full Talktime Value"),
            category = PlanCategory.TALKTIME,
            description = "Standard talktime recharge for calls and ISD services."
        )
    )

    private val airtelPlans = listOf(
        RechargePlan(
            id = "airtel_349",
            operator = Operator.AIRTEL,
            price = 349,
            validity = "28 Days",
            data = "1.5 GB/Day + 5G Plus",
            perks = listOf("Unlimited 5G Data", "Wynk Music Free", "Free Hellotunes", "Apollo 24|7"),
            category = PlanCategory.POPULAR,
            isBestSeller = true,
            promoTag = "BESTSELLER",
            description = "Unlimited 5G data, 1.5GB/day 4G, unlimited local/STD calls, 100 SMS/day, Wynk Music."
        ),
        RechargePlan(
            id = "airtel_399",
            operator = Operator.AIRTEL,
            price = 399,
            validity = "28 Days",
            data = "2.5 GB/Day + 5G Plus",
            perks = listOf("Unlimited 5G Data", "Disney+ Hotstar 3 Months", "Wynk Music"),
            category = PlanCategory.ENTERTAINMENT,
            isBestSeller = true,
            promoTag = "HOTSTAR BUNDLE",
            description = "Enjoy 2.5GB/day with free Disney+ Hotstar Mobile for 3 months and unlimited 5G."
        ),
        RechargePlan(
            id = "airtel_649",
            operator = Operator.AIRTEL,
            price = 649,
            validity = "56 Days",
            data = "2 GB/Day + 5G Plus",
            perks = listOf("Unlimited 5G Data", "Airtel Xstream Play 15+ OTTs", "Apollo 24|7"),
            category = PlanCategory.DAILY_DATA,
            promoTag = "56 DAYS",
            description = "56 days of high-speed 2GB/day data, unlimited 5G, and 15+ OTT channels."
        ),
        RechargePlan(
            id = "airtel_859",
            operator = Operator.AIRTEL,
            price = 859,
            validity = "84 Days",
            data = "1.5 GB/Day + 5G Plus",
            perks = listOf("Unlimited 5G Data", "84 Days Validity", "Wynk Music", "Free Hellotunes"),
            category = PlanCategory.LONG_VALIDITY,
            isBestSeller = true,
            promoTag = "BEST VALUE 84D",
            description = "The most popular 84-day quarterly pack with unlimited 5G and daily 1.5GB data."
        ),
        RechargePlan(
            id = "airtel_1199",
            operator = Operator.AIRTEL,
            price = 1199,
            validity = "84 Days",
            data = "2.5 GB/Day + 5G Plus",
            perks = listOf("Amazon Prime Membership", "Disney+ Hotstar", "Unlimited 5G"),
            category = PlanCategory.ENTERTAINMENT,
            promoTag = "PRIME + HOTSTAR",
            description = "All-in-one entertainment powerhouse with Amazon Prime & Disney+ Hotstar."
        ),
        RechargePlan(
            id = "airtel_3599",
            operator = Operator.AIRTEL,
            price = 3599,
            validity = "365 Days (1 Year)",
            data = "2 GB/Day + 5G Plus",
            perks = listOf("Unlimited 5G for 1 Year", "Apollo 24|7", "Wynk Music"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "ANNUAL 365 DAYS",
            description = "Full 365-day validity with daily 2GB data, unlimited 5G, and unlimited calls."
        ),
        RechargePlan(
            id = "airtel_22",
            operator = Operator.AIRTEL,
            price = 22,
            validity = "1 Day",
            data = "1 GB Data",
            voice = "Data Only",
            sms = "No SMS",
            perks = listOf("Instant Booster"),
            category = PlanCategory.DATA_BOOSTER,
            description = "Instant 1GB high speed data voucher."
        ),
        RechargePlan(
            id = "airtel_77",
            operator = Operator.AIRTEL,
            price = 77,
            validity = "Active Base Plan",
            data = "5 GB Data",
            voice = "Data Only",
            sms = "No SMS",
            perks = listOf("Existing Validity"),
            category = PlanCategory.DATA_BOOSTER,
            description = "5 GB extra data valid alongside your current active plan."
        ),
        RechargePlan(
            id = "airtel_100",
            operator = Operator.AIRTEL,
            price = 100,
            validity = "Unlimited",
            data = "None",
            voice = "₹81.75 Talktime",
            sms = "Standard rates",
            perks = listOf("Talktime Balance"),
            category = PlanCategory.TALKTIME,
            description = "Talktime balance for ISD and non-pack calling."
        )
    )

    private val viPlans = listOf(
        RechargePlan(
            id = "vi_349",
            operator = Operator.VI,
            price = 349,
            validity = "28 Days",
            data = "1.5 GB/Day + Hero Unlimited",
            perks = listOf(
                "Binge All Night (12am-6am Free Unlimited Data)",
                "Weekend Data Rollover",
                "Data Delights (2GB backup/month)"
            ),
            category = PlanCategory.POPULAR,
            isBestSeller = true,
            promoTag = "HERO UNLIMITED",
            description = "Hero Unlimited with zero data deduction from 12 AM to 6 AM, plus unused data rollover on weekends."
        ),
        RechargePlan(
            id = "vi_449",
            operator = Operator.VI,
            price = 449,
            validity = "28 Days",
            data = "3 GB/Day + Hero Unlimited",
            perks = listOf("Binge All Night", "Weekend Data Rollover", "Vi Movies & TV"),
            category = PlanCategory.DAILY_DATA,
            promoTag = "HEAVY USAGE",
            description = "Massive 3GB daily data with free midnight unlimited streaming and browsing."
        ),
        RechargePlan(
            id = "vi_719",
            operator = Operator.VI,
            price = 719,
            validity = "71 Days",
            data = "1.5 GB/Day + Hero Unlimited",
            perks = listOf("Binge All Night", "Weekend Data Rollover", "Truly Unlimited Calls"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "71 DAYS",
            description = "71 days of non-stop connection with Hero Unlimited perks."
        ),
        RechargePlan(
            id = "vi_859",
            operator = Operator.VI,
            price = 859,
            validity = "84 Days",
            data = "1.5 GB/Day + Hero Unlimited",
            perks = listOf("Binge All Night", "Weekend Rollover", "100 SMS/Day"),
            category = PlanCategory.LONG_VALIDITY,
            isBestSeller = true,
            promoTag = "BESTSELLER 84D",
            description = "The flagship Vi 84-day pack with unlimited calls and full Hero benefits."
        ),
        RechargePlan(
            id = "vi_3099",
            operator = Operator.VI,
            price = 3099,
            validity = "365 Days",
            data = "2 GB/Day + Hero Unlimited",
            perks = listOf("Disney+ Hotstar 1 Year", "Binge All Night", "Weekend Rollover"),
            category = PlanCategory.ENTERTAINMENT,
            promoTag = "1 YEAR + HOTSTAR",
            description = "Annual pack with 1 Year Disney+ Hotstar subscription and 2GB daily data."
        ),
        RechargePlan(
            id = "vi_19",
            operator = Operator.VI,
            price = 19,
            validity = "1 Day",
            data = "1 GB Data",
            voice = "Data Only",
            sms = "No SMS",
            perks = listOf("Quick Booster"),
            category = PlanCategory.DATA_BOOSTER,
            description = "Emergency 1GB data booster pack."
        ),
        RechargePlan(
            id = "vi_49",
            operator = Operator.VI,
            price = 49,
            validity = "1 Day",
            data = "20 GB Cricket Pack",
            voice = "Data Only",
            sms = "No SMS",
            perks = listOf("20 GB High Speed Data"),
            category = PlanCategory.DATA_BOOSTER,
            promoTag = "CRICKET 20GB",
            description = "Watch live cricket matches with 20GB lump-sum 1-day data."
        )
    )

    private val bsnlPlans = listOf(
        RechargePlan(
            id = "bsnl_107",
            operator = Operator.BSNL,
            price = 107,
            validity = "35 Days",
            data = "3 GB Total",
            voice = "200 Mins Voice Calls",
            sms = "No SMS",
            perks = listOf("BSNL Tunes", "Affordable Budget Pack"),
            category = PlanCategory.POPULAR,
            isBestSeller = true,
            promoTag = "BUDGET SAVER",
            description = "Super affordable 35-day validity pack with 3GB data and 200 calling minutes."
        ),
        RechargePlan(
            id = "bsnl_197",
            operator = Operator.BSNL,
            price = 197,
            validity = "70 Days",
            data = "2 GB/Day (for 18 days)",
            voice = "Unlimited Calls (for 18 days)",
            sms = "100 SMS/Day (18 days)",
            perks = listOf("Long 70 Days Validity"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "70 DAYS SIM ACTIVE",
            description = "Keeps your SIM active for 70 days at rock-bottom price."
        ),
        RechargePlan(
            id = "bsnl_397",
            operator = Operator.BSNL,
            price = 397,
            validity = "150 Days",
            data = "2 GB/Day (30 days)",
            voice = "Unlimited Calls (30 days)",
            sms = "100 SMS/Day (30 days)",
            perks = listOf("150 Days Validity", "Free BSNL Tunes"),
            category = PlanCategory.LONG_VALIDITY,
            isBestSeller = true,
            promoTag = "150 DAYS",
            description = "5 months of incoming validity with 30 days of full unlimited benefits."
        ),
        RechargePlan(
            id = "bsnl_599",
            operator = Operator.BSNL,
            price = 599,
            validity = "84 Days",
            data = "3 GB/Day + Free Night Data",
            voice = "Truly Unlimited Calls",
            sms = "100 SMS/Day",
            perks = listOf("Free Night Data (12 AM - 5 AM)", "Zing Music"),
            category = PlanCategory.DAILY_DATA,
            promoTag = "3GB/DAY VALUE",
            description = "Incredible value: 3GB/day data for 84 days plus completely free night data."
        ),
        RechargePlan(
            id = "bsnl_1999",
            operator = Operator.BSNL,
            price = 1999,
            validity = "365 Days",
            data = "600 GB Total",
            voice = "Truly Unlimited Calls",
            sms = "100 SMS/Day",
            perks = listOf("600 GB Lump-sum Data", "BSNL Tunes 365 Days", "Eros Now"),
            category = PlanCategory.LONG_VALIDITY,
            promoTag = "365 DAYS ANNUAL",
            description = "Best annual value pack in India with 600GB lump-sum data and full year calling."
        )
    )

    // Indian DTH Operators
    val dthOperators = listOf(
        DthOperator(
            id = "tata_play",
            name = "Tata Play (Tata Sky)",
            sampleId = "1098765432",
            minAmount = 150,
            popularPlans = listOf(
                DthPlan("Hindi Super Value", 249, "1 Month", "180+ Channels", "Popular Hindi GEC + Movies + News"),
                DthPlan("Dhamaal Kids & Sports", 399, "1 Month", "240+ Channels", "Star Sports, Sony Sports + Cartoon Network"),
                DthPlan("Tata Play Binge Mega", 499, "1 Month", "300+ Channels + OTT", "Includes Disney+ Hotstar, SonyLIV, ZEE5")
            )
        ),
        DthOperator(
            id = "airtel_dth",
            name = "Airtel Digital TV",
            sampleId = "3001234567",
            minAmount = 150,
            popularPlans = listOf(
                DthPlan("Airtel Dabang Hindi", 270, "1 Month", "190+ Channels", "All Hindi entertainment & infotainment"),
                DthPlan("Mega HD Entertainment", 420, "1 Month", "280+ Channels HD", "50+ Full HD Channels included")
            )
        ),
        DthOperator(
            id = "dish_tv",
            name = "Dish TV",
            sampleId = "02512345678",
            minAmount = 100,
            popularPlans = listOf(
                DthPlan("Classic Hindi Pack", 210, "1 Month", "170+ Channels", "Family entertainment & news"),
                DthPlan("Super Sports HD", 380, "1 Month", "220+ Channels", "Live Cricket & Football in HD")
            )
        ),
        DthOperator(
            id = "sun_direct",
            name = "Sun Direct",
            sampleId = "4012345678",
            minAmount = 120,
            popularPlans = listOf(
                DthPlan("South Gold Pack", 230, "1 Month", "160+ Channels", "Tamil, Telugu, Kannada, Malayalam hits")
            )
        )
    )
}
