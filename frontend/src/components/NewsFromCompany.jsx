import React, { useState, useEffect, useRef } from "react";

export default function NewsFromCompany() {
  const [news, setNews] = useState(null);
  const scrollRef = useRef(null);

  // Set message from API
  useEffect(() => {
    setNews(
      "Raytheon CEO has just announced a $10 billion project with the United States Space Force for new a groundbreaking rocket program! 🚀 Consequat amet irure ad enim qui id cupidatat proident do. Non occaecat veniam nulla duis sit nisi tempor labore ut voluptate cillum aliquip minim. Laboris commodo anim Lorem qui proident aliquip nostrud sunt consequat in tempor ut reprehenderit aute. Minim Lorem aliqua aliquip minim fugiat cupidatat exercitation. Lorem tempor occaecat et laboris. In do anim irure ullamco magna nulla cillum tempor cillum ea officia exercitation aute. Aliqua amet qui nostrud et. Laboris labore proident laborum mollit nulla aliquip eu aute elit enim. Nostrud excepteur velit mollit commodo voluptate nostrud enim eu ex magna id in laborum. Aute tempor enim consequat aliqua aliquip tempor consequat velit consectetur est. Esse tempor sint velit aliqua.",
    );
  }, []);

  useEffect(() => {
    if (scrollRef.current) {
      const scrollElement = scrollRef.current;
      let scrollAmount = 0;
      const step = 1;
      const interval = 50;

      const startScrolling = () => {
        const scrollInterval = setInterval(() => {
          if (
            scrollAmount <
            scrollElement.scrollHeight - scrollElement.clientHeight
          ) {
            scrollAmount += step;
            scrollElement.scrollTo(0, scrollAmount);
          } else {
            clearInterval(scrollInterval);
            setTimeout(() => {
              scrollAmount = 0;
              scrollElement.scrollTo(0, 0);
              setTimeout(startScrolling, 5000);
            }, 5000);
          }
        }, interval);

        return () => clearInterval(scrollInterval);
      };

      setTimeout(startScrolling, 5000);
    }
  }, [news]);

  return (
    <div className="dashboard-component flex h-1/2 flex-col bg-custom-white">
      <h1 className="w-full p-4 pb-0 text-center text-2xl font-semibold">
        News from Company
      </h1>
      <div
        ref={scrollRef}
        className="scrollbar-none m-6 flex h-32 overflow-y-scroll scroll-auto rounded-3xl bg-gray-200 p-4 px-6 text-start text-lg font-medium shadow-inner"
      >
        {news || "No message from manager"}
      </div>
    </div>
  );
}
